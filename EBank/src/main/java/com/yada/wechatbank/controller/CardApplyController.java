package com.yada.wechatbank.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.query.CardApplyQuery;
import com.yada.wechatbank.service.CardApplyService;
import com.yada.wechatbank.util.IdentyTypeUtil;
import com.yada.wechatbank.util.TokenUtil;

/**
 * 预约办卡进度查询 Created by QinQiang on 2016/4/11.
 */
@Controller
@RequestMapping(value = "cardapply")
public class CardApplyController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String LIST_URL = "wechatbank_pages/CardApply/list";
	private static final String DETAIL_URL = "wechatbank_pages/CardApply/detail";

	@Autowired
	private CardApplyService cardApplyService;

	/**
	 * 初始化申请进度查询页面
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) {
		// TODO QQ 添加分享转发代码
		TokenUtil.addToken(request);
		return LIST_URL;
	}

	/**
	 * 预约办卡进度查询
	 *
	 * @param cardApplyQuery
	 *            查询条件
	 * @param model
	 *            Model
	 * @return URL
	 */
	@RequestMapping(value = "listP")
	public String listP(@ModelAttribute("formBean") CardApplyQuery cardApplyQuery, Model model) {
		String name = cardApplyQuery.getName();
		String identityType = cardApplyQuery.getIdentityType();
		String identityNo = cardApplyQuery.getIdentityNo();
		int currPage = cardApplyQuery.getCurrPage();
		int nextPage = currPage + 1;

		// 检查查询要素
		if (name == null || name.isEmpty() || identityType == null || identityType.isEmpty() || identityNo == null || identityNo.isEmpty()) {
			model.addAttribute("cardApplyList", new CardApplyList());
			return DETAIL_URL;
		}

		// 预约办卡进度查询
		logger.info("@CardApply@预约办卡进度查询，参数：[name={},identityType={},identityNo={},currentPage={}]", name, identityType, identityNo, nextPage);
		CardApplyList cardApplyList = cardApplyService.getCrdCardSchedule(name, identityType, identityNo, nextPage);
		logger.info("@CardApply@预约办卡进度查询，结果：[{}]", cardApplyList);

		model.addAttribute("model", cardApplyQuery);
		model.addAttribute("cardApplyList", cardApplyList);
		model.addAttribute("currPage", nextPage);

		return DETAIL_URL;
	}

	/**
	 * 获取短信验证码
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param identityType
	 *            证件类型
	 * @param identityNo
	 *            证件号
	 * @param mobileNo
	 *            手机号
	 * @param verificationCode
	 *            验证码
	 * @return String
	 */
	@RequestMapping(value = "getMsgCode_ajax")
	@ResponseBody
	public String getMsgCode_ajax(HttpServletRequest request, String identityType, String identityNo, String mobileNo, String verificationCode) {
		// 证件类型转换为gcs的需要的类型
		identityType = IdentyTypeUtil.idTypeZxToGcs(identityType);
		if ("".equals(identityType)) {
			return "errorIdType";
		}
		// Session中存储的验证码
		String randomCode = (String) request.getSession().getAttribute("jcmsrandomchar");
		// 验证图片验证码
		if (verificationCode == null || !verificationCode.equalsIgnoreCase(randomCode)) {
			return "errorCode";
		}
		// 验证要素是否为空
		if (identityNo == null || identityNo.isEmpty() || mobileNo == null || mobileNo.isEmpty()) {
			return "exception";
		}
		// 验证Token
		if (!TokenUtil.validateCode(request)) {
			TokenUtil.removeCode(request);
			return "exception";
		}
		// 验证成功后Remove
		TokenUtil.removeCode(request);

		// 发送短信验证码
		logger.info("@CardApply@预约办卡发送短信验证码，参数：[identityType={},identityNo={},mobileNo={}]", identityType, identityNo, mobileNo);
		String sendResult = cardApplyService.sendCardApplySMS(identityType, identityNo, mobileNo);
		logger.info("@CardApply@预约办卡发送短信验证码，参数：[identityType={},identityNo={},mobileNo={}]，结果：[{}]", identityType, identityNo, mobileNo, sendResult);

		// 重新生成Token并返回
		TokenUtil.addToken(request);
		String result = sendResult + "," + request.getSession().getAttribute("keyInSession");
		return result;
	}

	/**
	 * 验证短信验证码
	 *
	 * @param identityNo
	 *            证件号
	 * @param mobileNo
	 *            手机号
	 * @param code
	 *            验证码
	 * @return String
	 */
	@RequestMapping(value = "checkMsgCode_ajax")
	@ResponseBody
	public String checkMsgCode_ajax(String identityNo, String mobileNo, String code) {
		logger.info("@CardApply@预约办卡验证短信验证码，参数：[identityNo={},mobileNo={},code={}]", identityNo, mobileNo, code);
		String result = Boolean.toString(cardApplyService.checkCardApplySMSCode(identityNo, mobileNo, code)).toLowerCase();
		logger.info("@CardApply@预约办卡验证短信验证码，参数：[identityNo={},mobileNo={},code={}]，结果：[{}]", identityNo, mobileNo, code, result);
		return result;
	}

}
