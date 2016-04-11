package com.yada.wechatbank.controller;

import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.service.CardHolderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;

@Controller
@RequestMapping(value = "cardholderinfo")
public class CardHolderInfoController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CardHolderInfoService cardHolderInfoService;
	private static final String LISTURL="wechatbank_pages/CardHolderInfo/list";

	/**
	 * 用户个人信息查询
	 * @param model  页面model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {

		//TODO 通过登录用户信息获取卡号
		String cardNo="";
		CardHolderInfo cardHolderInfo=cardHolderInfoService.getCardHolderInfo(cardNo);
		//判断是否获取到数据
		if (cardHolderInfo == null) {
			return BUSYURL;
		}
		logger.debug("@WDZL@根据卡号[{}]查询客户信息，获取到的客户信息为[{}]",cardNo,cardHolderInfo);
		model.addAttribute("cardHolderInfo", cardHolderInfo);
		return LISTURL;
	}
}
