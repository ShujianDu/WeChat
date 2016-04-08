package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yada.wechatbank.model.CardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.query.BindingQuery;
import com.yada.wechatbank.service.BindingManager;
import com.yada.wechatbank.shiro.ValidateTime;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.JsMapUtil;
import com.yada.wechatbank.util.TokenUtil;

/**
 * 客户身份绑定、默认卡绑定
 * 
 * @author zm
 * 
 */
@Controller
@RequestMapping(value = "binding")
public class BindingController {
	private final static Logger logger = LoggerFactory.getLogger(BindingController.class);
	@Autowired
	private BindingManager bindingManager;
	@Autowired
	private ValidateTime validateTime;
	private static final String BOUNDURL = "wechatbank_pages/Binding/bound";
	private static final String BINDLISTURL = "wechatbank_pages/Binding/binding";
	private static final String BINDCARDURL = "wechatbank_pages/Binding/bindingDefCard";
	private static final String SUCCESSURL = "wechatbank_pages/Binding/success";
	private static final String ERRORURL = "wechatbank_pages/Binding/error";
	private static final String LOCK = "wechatbank_pages/Binding/lock";
	private static final String FILLIDTYPEURL = "wechatbank_pages/Binding/fillIdType";
	private static String ERROR = "wechatbank_pages/error";
	public static final String BUSYURL = "wechatbank_pages/busy";
	public static final String NOCARDURL = "wechatbank_pages/nocard";
	// 是否是默认卡(0 是，1 否)
	private static final String ISDEFAULT = "0";
	private static final String NODEFAULT = "1";
	

	/**
	 * 进入客户绑定界面
	 * 
	 * @param bindingQuery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(
			@ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
			HttpServletRequest request) {
		String openId = (String) request.getAttribute("openId");
		if(openId!=null && !"".equals(openId)){
			bindingQuery.setOpenId(openId);
		}
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"binding/list.do","中国银行信用卡绑定业务");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		Boolean rmiReturn = bindingManager.validateIsBinding(openId);
		logger.info("@**BD@调用核心根据openId["+openId+"]判断用户是否绑定,判断结果rmiReturn["+rmiReturn+"]");
		// 判断是否已经绑定
		if (rmiReturn) {
			model.addAttribute("openId", openId);
			return BOUNDURL;
		}
		model.addAttribute("model", bindingQuery);
		TokenUtil.addToken(request);
		return BINDLISTURL;
	}


	/**
	 * 客户身份绑定
	 * 
	 * @param bindingQuery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bindingM")
	public String bindingM(
			@ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
			HttpServletRequest request) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"binding/bindDefCard.do","中国银行信用卡绑定业务");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		if(bindingManager.isLocked(bindingQuery.getOpenId(),bindingQuery.getIdNumber())){
			return LOCK;
		}
		String mobileCode=request.getParameter("mobileCode");
		//短信 验证码未验证通过
		if (!checkBindingSMSCode(bindingQuery.getOpenId(),bindingQuery.getIdNumber(),mobileCode)) {
			model.addAttribute("msg", "3");
			model.addAttribute("model", bindingQuery);
			return BINDLISTURL;
		}
        //获取session中存储的短信验证码的证件号	
		String smsIdNum = (String) request.getSession().getAttribute("idNum");
        //两次的证件号是否一致		
		if(!bindingQuery.getIdNumber().equals(smsIdNum)){
			bindingManager.addCountCache(bindingQuery.getOpenId(),bindingQuery.getIdNumber());
			model.addAttribute("msg", "2");
			model.addAttribute("model", bindingQuery);
			return BINDLISTURL;
		}
		String bindingResult = bindingManager.custBinding(bindingQuery.getOpenId(),bindingQuery.getIdType(),
				bindingQuery.getIdNumber(), bindingQuery.getPasswordQuery(), bindingQuery.getToUser());
		logger.info("@**BD@调用核心根据openId["+bindingQuery.getOpenId()+"],idNumber["+bindingQuery.getIdNumber()+"],idType["+bindingQuery.getIdType()+"],"
				+"toUser["+bindingQuery.getToUser()+"]进行用户绑定,绑定结果bindingResult["+bindingResult+"]");
		// 查不到卡号
		if ("1".equals(bindingResult)) {
			logger.debug("@**BD@查询不到卡号");
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", bindingResult);
		} else if ("2".equals(bindingResult)) { // 验密失败
			logger.debug("@**BD@验密失败openId["+bindingQuery.getOpenId()+"]");
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", bindingResult);
		}else if ("0".equals(bindingResult)) { // 成功
			// 调用RMI 获取卡列表
			List<CardInfo> cardList = bindingManager.selectCardNOs(bindingQuery.getOpenId());
			logger.info("@**BD@调用核心根据openId["+bindingQuery.getOpenId()+"]获取卡列表，获取到的卡列表cardList["+cardList+"]");
			// RMI返回值为空或没有数据
			if (cardList == null) {
				logger.debug("@**BD@获取到的卡列表为空或没有数据,openId["+bindingQuery.getOpenId()+"]");
				return BUSYURL;
			} else if (cardList.size() == 0) {
				logger.debug("@**BD@获取到的卡列表长度为0,openId["+bindingQuery.getOpenId()+"]");
				return NOCARDURL;
			} else {
				//为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合			
				List<String> cardListCrypt = new ArrayList<String>(); 
				for (CardInfo cardInfo : cardList){
					String cardNo = cardInfo.getCardNo();
					cardListCrypt.add(cardNo);
				}
				try {
					Crypt.cardNoCrypt(cardListCrypt);
				} catch (Exception e) {
					logger.debug("@**BD@加密卡号出现异常" + e);
					return BUSYURL;
				}
				// 将卡号信息集合放入Session中
				request.getSession().setAttribute("session_defaultCardList",cardList);
//				model.addAttribute("cardList", cardList);
				model.addAttribute("cardListCrypt", cardListCrypt);
				model.addAttribute("model", bindingQuery);
			}
			// 页面分享js需要的参数
			jsMap = JsMapUtil.getJsMapConfig(request,
					"binding/bindDefCard.do","中国银行信用卡绑定业务");
			if (jsMap == null) {
				return ERROR;
			}
			for (String key : jsMap.keySet()) {
				model.addAttribute(key, jsMap.get(key));
			}
			return BINDCARDURL;
		} else {
			return ERRORURL;
		}
		return BINDLISTURL;
	}
	
	
	
	
	/**
	 * 默认卡绑定 需通过openId、authCode权限验证
	 * 
	 * @param bindingQuery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bindDefCard")
	public String bindDefCard(
			@ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
			HttpServletRequest request) {
		String openId = request.getParameter("openId");
		if(openId==null || "".equals(openId)){
			openId = (String) request.getAttribute("openId");
			bindingQuery.setOpenId(openId);
		}
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"binding/bindDefCard.do","中国银行信用卡绑定业务");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}

		//查询数据库中的客户信息的证件类型有无数据
		if(bindingManager.isExistIdType(openId).get("isexist").equals("false")){
			model.addAttribute("idNum", bindingManager.isExistIdType(openId).get("idNum"));
			model.addAttribute("model", bindingQuery);
			return FILLIDTYPEURL;
		}
		
		// 调用RMI 获取默认卡列表
		String defCardNo = bindingManager.getDefCardNo(openId);
		logger.info("@GHMRK@调用核心根据openId["+openId+"]获取默认卡，获取到的默认卡defCardNo["+defCardNo+"]");
		if (defCardNo == null) {
			 logger.debug("@GHMRK@获取到的默认卡为null,openId["+bindingQuery.getOpenId()+"]");
			defCardNo = "";
		}
		// 调用RMI 获得卡号列表
		List<CardInfo> cardList = bindingManager.selectCardNOs(bindingQuery.getOpenId());
		logger.info("@GHMRK@调用核心根据openId["+openId+"]获取卡列表，获取到的卡列表cardList["+cardList+"]");
		// RMI返回值为空或没有数据
		if (cardList == null) {
			logger.debug("@GHMRK@获取到的卡列表为null,openId["+bindingQuery.getOpenId()+"]");
			return BUSYURL;
		} else if (cardList.size() == 0) {
			logger.debug("@GHMRK@获取到的卡列表长度为0,openId["+bindingQuery.getOpenId()+"]");
			return NOCARDURL;
		} else {
			//为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合			
			List<String> cardListCrypt = new ArrayList<String>(); 
			for (CardInfo cardInfo : cardList){
				String cardNo = cardInfo.getCardNo();
				cardListCrypt.add(cardNo);
			}
			try {
				Crypt.cardNoCrypt(cardListCrypt);
			} catch (Exception e) {
				logger.debug("@GHMRK@加密卡号出现异常" + e);
				return BUSYURL;
			}
			// 将卡号信息集合放入Session中
			request.getSession().setAttribute("session_defaultCardList",cardList);
			model.addAttribute("defCardNo", defCardNo);
//			model.addAttribute("cardList", cardList);
			model.addAttribute("cardListCrypt", cardListCrypt);
			model.addAttribute("model", bindingQuery);
		}
		return BINDCARDURL;
	}

	/**
	 * 默认卡绑定 需通过身份验证码验证
	 * 
	 * @param bindingQuery
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bindDefCardP")
	public String bindDefCardP(
			@ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<CardInfo> cardList = (List<CardInfo>) request.getSession().getAttribute("session_defaultCardList");
		String defCardNO = bindingQuery.getDefaultCard();
		try {
			defCardNO = Crypt.decode(defCardNO);
		} catch (Exception e) {
			logger.info("@GHMRK@卡号"+ defCardNO + "解密出现异常" + e);
			return ERRORURL;
		}
		if (cardList != null) {
			// 对卡片信息集合进行是否是默认卡处理 (0 是，1 否)
			for (CardInfo entity : cardList) {
				if (defCardNO.equals(entity.getCardNo())) {
					entity.setIsDefault(ISDEFAULT);
				} else {
					entity.setIsDefault(NODEFAULT);
				}
			}
		}
		boolean dcbReturn = bindingManager.defCardBinding(bindingQuery.getOpenId(), cardList);
		logger.info("@GHMRK@调用核心根据openId["+bindingQuery.getOpenId()+"]绑定默认卡，默认卡绑定结果dcbReturn["+dcbReturn+"]");
		// 调用RMI 绑定默认卡并返回结果 失败进入绑定失败界面 成功进入绑定成功界面
		if (dcbReturn) {
			// 定义绑定成功跳转的url
			return SUCCESSURL;
		} else {
			// 定义绑定失败跳转的url
			return ERRORURL;
		}
		
	}
	
	
	
	/**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getSMSCode_ajax")
	public void getSMSCode_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "false";
		String identityType=request.getParameter("idType");
		String identity=request.getParameter("identityNo");
		String openId=request.getParameter("openId");
		
		// Session中存储的验证码
		String Randomcode_yz = (String) request.getSession().getAttribute(
						"jcmsrandomchar");
		String verificationCode = request.getParameter("verificationCode");
		String mobilNo = request.getParameter("mobilNo");
		if (verificationCode != null
				&& verificationCode.equalsIgnoreCase(Randomcode_yz)) {
//			if (TokenUtil.validateCode(request)) {
//				TokenUtil.removeCode(request);
				if(!"".equals(identity)&&!"".equals(openId)&& !"".equals(mobilNo))
				{
					
					String sendResult = bindingManager.bindingSend(identityType,identity,openId+identity, mobilNo);
					logger.info("@**BD@调用核心根据identityType[" + identityType +"]identity["+identity+"],openId["+openId+"]发送短信验证码,绑定结果sendResult["+sendResult+"]");
					if (sendResult == null) {
					    logger.debug("@**BD@绑定默认卡失败,openId["+openId+"],identityType["+identityType+"],identity["+identity+"]");
						result = "exception";
					} else
					{
                        //验证码发送成功记录证件号，防止黑客修改卡号后再验证查询密码						
						if("true".equals(sendResult)){
							request.getSession().setAttribute("idNum", identity);
						}
//				        result=sendResult;
				        TokenUtil.addToken(request);
						result = sendResult+","+request.getSession().getAttribute("keyInSession");
					}
				}
//			} else {
//				result = "keycodeexception";
//				TokenUtil.removeCode(request);
//			}
		}else {
//			TokenUtil.removeCode(request);
			result = "errorCode";
		}
		response.getWriter().print(result);
		response.getWriter().flush();
	}
	
	/**
	 * 验证短信验证码
	 * 
	 * @param openId
	 * @param idNumber
	 * @param mobileCode
	 * @throws IOException
	 */
	public boolean checkBindingSMSCode(String openId,String idNumber,String mobileCode) {
		return  bindingManager.bindingVerificationCode(openId,idNumber, mobileCode);
	}
	
	
	@RequestMapping(value = "duty")
	public String duty(Model model, HttpServletRequest request) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"binding/duty.do","中国银行信用卡绑定业务");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		return "wechatbank_pages/Binding/duty";
	}
	
	@RequestMapping(value = "fillIdentityType")
	public String fillIdentityType(@ModelAttribute("formBean") BindingQuery bindingQuery, Model model,HttpServletRequest request){
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"binding/list.do","中国银行信用卡绑定业务");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		String openId=request.getParameter("openId");
		String identityNo=request.getParameter("idNumber");
		String identityType=request.getParameter("idType");

		if(!bindingManager.isCorrectIdentityType(identityNo,identityType))
		{
			model.addAttribute("idNum", identityNo);
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", "1");
			return FILLIDTYPEURL;
		}
		
		if(bindingManager.fillIdentityType(openId,identityNo,identityType)){
			// 调用RMI 获取默认卡列表
			String defCardNo = bindingManager.getDefCardNo(openId);
			logger.info("@GHMRK@调用核心根据openId["+openId+"]获取默认卡，获取到的默认卡defCardNo["+defCardNo+"]");
			if (defCardNo == null) {
				logger.debug("@GHMRK@获取到的默认卡为null,openId["+bindingQuery.getOpenId()+"]");
				defCardNo = "";
			}
			// 调用RMI 获得卡号列表
			List<CardInfo> cardList = bindingManager.selectCardNOs(bindingQuery.getOpenId());
			logger.info("@GHMRK@调用核心根据openId["+openId+"]获取卡列表，获取到的卡列表cardList["+cardList+"]");
			// RMI返回值为空或没有数据
			if (cardList == null) {
				logger.debug("@GHMRK@获取到的卡列表为null,openId["+bindingQuery.getOpenId()+"]");
				return BUSYURL;
			} else if (cardList.size() == 0) {
				logger.debug("@GHMRK@获取到的卡列表长度为0,openId["+bindingQuery.getOpenId()+"]");
				return NOCARDURL;
			} else {
				//为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合			
				List<String> cardListCrypt = new ArrayList<String>(); 
				for (CardInfo cardInfo : cardList){
					String cardNo = cardInfo.getCardNo();
					cardListCrypt.add(cardNo);
				}
				try {
					Crypt.cardNoCrypt(cardListCrypt);
				} catch (Exception e) {
					logger.debug("@BCIDNO@加密卡号出现异常" + e);
					return BUSYURL;
				}
				// 将卡号信息集合放入Session中
				request.getSession().setAttribute("session_defaultCardList",cardList);
				model.addAttribute("defCardNo", defCardNo);
//				model.addAttribute("cardList", cardList);
				model.addAttribute("cardListCrypt", cardListCrypt);
				model.addAttribute("model", bindingQuery);
			}
			return BINDCARDURL;
		}
		return BUSYURL;
	}
	
	@RequestMapping(value = "locked")
	public String locked(Model model,
			HttpServletRequest request){
		return LOCK;
	}
	}
