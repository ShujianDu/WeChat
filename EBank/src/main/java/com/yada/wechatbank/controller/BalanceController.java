package com.yada.wechatbank.controller;

import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.query.BalanceQuery;
import com.yada.wechatbank.service.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.model.CardInfo;
import java.util.List;

/**
 * 我的额度
 * @author tx
 */
@Controller
@RequestMapping(value = "balance")
class BalanceController {
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());

	@Autowired
	private BalanceService balanceService;
	private static final String LISTURL = "wechatbank_pages/Balance/list";
	// 数据查询异常跳转URL
	public static final String BUSYURL = "wechatbank_pages/busy";

	/**
	 * 额度查询
	 * 
	 * @param balanceQuery 页面调用传值实体
	 * @param model        页面model
	 * @return 额度页面/错误页面
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") BalanceQuery balanceQuery, Model model) {
		//TODO 通过登录人信息获取证件号
		List<CardInfo> cardinfos=null;
		List<List<Balance>> newList = balanceService.getList(cardinfos);
		//TODO 获取用户证件号
		String identityNo="";
		if (newList == null) {
			logger.warn("@WDED@获取到的额度集合为null，identityNo[" + identityNo + "]");
			return BUSYURL;
		}
		logger.debug("@WDED@通过证件号[{}]获取到的额度集合为[{}]", identityNo,newList);
		model.addAttribute("newList", newList);
		return LISTURL;
	}
}

