package com.yada.wechatbank.controller;

import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.service.CardHolderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "cardholderinfo")
public class CardHolderInfoController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CardHolderInfoService cardHolderInfoServiceImpl;

	/**
	 * 用户个人信息查询
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request,Model model) {

		String identityNo=getIdentityNo(request);
		String identityType=getIdentityType(request);
		CardHolderInfo cardHolderInfo= cardHolderInfoServiceImpl.getCardHolderInfo(identityType,identityNo);
		//判断是否获取到数据
		if (cardHolderInfo == null) {
			return BUSYURL;
		}
		logger.debug("@WDZL@根据证件类型[{}]，证件号[{}]查询客户信息，获取到的客户信息为[{}]",identityType,identityNo,cardHolderInfo);
		model.addAttribute("cardHolderInfo", cardHolderInfo);
		return "wechatbank_pages/CardHolderInfo/list";
	}
}
