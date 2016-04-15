package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BindingService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.query.BindingQuery;
import com.yada.wechatbank.util.JsMapUtil;
import com.yada.wechatbank.util.TokenUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 客户身份绑定、默认卡绑定
 * 
 * @author zm
 * 
 */
@Controller
@RequestMapping(value = "binding")
public class BindingController extends BaseController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String BOUNDURL = "wechatbank_pages/Binding/bound";
	private static final String BINDLISTURL = "wechatbank_pages/Binding/binding";
	private static final String BINDCARDURL = "wechatbank_pages/Binding/bindingDefCard";
	private static final String SUCCESSURL = "wechatbank_pages/Binding/success";
	private static final String ERRORURL = "wechatbank_pages/Binding/error";
	private static final String LOCK = "wechatbank_pages/Binding/lock";
	private static final String FILLIDTYPEURL = "wechatbank_pages/Binding/fillIdType";
	// 是否是默认卡(0 是，1 否)
	private static final String ISDEFAULT = "0";
	private static final String NODEFAULT = "1";
	@Autowired
	private BindingService bindingServiceImpl;

	/**
	 * 进入客户绑定界面
	 * @param bindingQuery 绑定查询实体
	 * @param model Model
	 * @param request HttpServletRequest
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
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"binding/list.do","中国银行信用卡绑定业务");
//		if (jsMap == null) {
//			return ERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		boolean rmiReturn = bindingServiceImpl.validateIsBinding(openId);
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
	 * @param bindingQuery 绑定查询实体
	 * @param model Model
	 * @param request HttpServletRequest
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
		if(bindingServiceImpl.isLocked(bindingQuery.getOpenId(),bindingQuery.getIdNumber())){
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
			bindingServiceImpl.addCountCache(bindingQuery.getOpenId(),bindingQuery.getIdNumber());
			model.addAttribute("msg", "2");
			model.addAttribute("model", bindingQuery);
			return BINDLISTURL;
		}
		String bindingResult = bindingServiceImpl.custBinding(bindingQuery.getOpenId(),bindingQuery.getIdType(),
				bindingQuery.getIdNumber(), bindingQuery.getPasswordQuery());
		// 查不到卡号
		if ("1".equals(bindingResult)) {
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", bindingResult);
		} else if ("2".equals(bindingResult)) { // 验密失败
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", bindingResult);
		}else if ("0".equals(bindingResult)) { // 成功
			// 调用RMI 获取卡列表
			List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(getIdentityNo(request),getIdentityType(request));
			// RMI返回值为空或没有数据
			if (cardList == null) {
				return BUSYURL;
			} else if (cardList.size() == 0) {
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
					return BUSYURL;
				}
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
	 * @param bindingQuery 绑定查询实体
	 * @param model Model
	 * @param request HttpServletRequest
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
		if(bindingServiceImpl.isExistIdType(openId).get("isexist").equals("false")){
			model.addAttribute("idNum", bindingServiceImpl.isExistIdType(openId).get("idNum"));
			model.addAttribute("model", bindingQuery);
			return FILLIDTYPEURL;
		}
		
		// 调用RMI 获取默认卡列表
		String defCardNo = bindingServiceImpl.getDefCardNo(openId);
		if (defCardNo == null) {
			defCardNo = "";
		}
		List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(getIdentityNo(request),getIdentityType(request));
		if (cardList == null) {
			return BUSYURL;
		} else if (cardList.size() == 0) {
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
				return BUSYURL;
			}
			model.addAttribute("defCardNo", defCardNo);
			model.addAttribute("cardListCrypt", cardListCrypt);
			model.addAttribute("model", bindingQuery);
		}
		return BINDCARDURL;
	}

	/**
	 * 默认卡绑定 需通过身份验证码验证
	 * 
	 * @param bindingQuery 绑定查询实体
	 * @param request HttpServletRequest
	 */
	@RequestMapping(value = "bindDefCardP")
	public String bindDefCardP(
			@ModelAttribute("formBean") BindingQuery bindingQuery, HttpServletRequest request) {
		String defCardNO = bindingQuery.getDefaultCard();
		try {
			defCardNO = Crypt.decode(defCardNO);
		} catch (Exception e) {
			return ERRORURL;
		}
		boolean dcbReturn = bindingServiceImpl.defCardBinding(bindingQuery.getOpenId(), defCardNO);
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
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "getSMSCode_ajax")
	@ResponseBody
	public String getSMSCode_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//TODO 获取短信验证码
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
				if(!"".equals(identity)&&!"".equals(openId)&& !"".equals(mobilNo))
				{
					
					String sendResult = bindingServiceImpl.bindingSend(identityType,identity,openId+identity, mobilNo);
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
		}else {
			result = "errorCode";
		}
		return result;
	}
	
	/**
	 * 验证短信验证码
	 * 
	 * @param openId  openID
	 * @param idNumber 证件号
	 * @param mobileCode 短信验证码
	 */
	private boolean checkBindingSMSCode(String openId, String idNumber, String mobileCode) {
		return  bindingServiceImpl.bindingVerificationCode(openId,idNumber, mobileCode);
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

		if(!bindingServiceImpl.isCorrectIdentityType(identityNo,identityType))
		{
			model.addAttribute("idNum", identityNo);
			model.addAttribute("model", bindingQuery);
			model.addAttribute("msg", "1");
			return FILLIDTYPEURL;
		}
		
		if(bindingServiceImpl.fillIdentityType(openId,identityNo,identityType)){
			// 获取默认卡
			String defCardNo = bindingServiceImpl.getDefCardNo(openId);
			if (defCardNo == null) {
				defCardNo = "";
			}
			// 调用RMI 获得卡号列表
			List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(getIdentityNo(request),getIdentityType(request));
			// RMI返回值为空或没有数据
			if (cardList == null) {
				return BUSYURL;
			} else if (cardList.size() == 0) {
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
					return BUSYURL;
				}
				model.addAttribute("defCardNo", defCardNo);
				model.addAttribute("cardListCrypt", cardListCrypt);
				model.addAttribute("model", bindingQuery);
				model.addAttribute("cardList", cardList);
			}
			return BINDCARDURL;
		}
		return BUSYURL;
	}
	
	@RequestMapping(value = "locked")
	public String locked(){
		return LOCK;
	}
}
