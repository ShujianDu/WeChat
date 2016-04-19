package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 我的额度
 * @author tx
 */
@Controller
@RequestMapping(value = "balance")
class BalanceController extends BaseController{
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());

	@Autowired
	private BalanceService balanceServiceImpl;
	private static final String LISTURL = "wechatbank_pages/Balance/list";

	/**
	 * 额度查询
	 * 
	 * @param balanceQuery 页面调用传值实体
	 * @param model        页面model
	 * @return 额度页面/错误页面
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request,@ModelAttribute("formBean") BalanceQuery balanceQuery, Model model) {
		String identityNo=getIdentityNo(request);
		String identityType=getIdentityType(request);
		List<List<Balance>> newList = balanceServiceImpl.getList(identityType,identityNo);
		if (newList == null) {
			logger.warn("@WDED@获取到的额度集合为null，identityNo[" + identityNo + "]");
			return BUSYURL;
		}
		logger.debug("@WDED@通过证件号[{}]获取到的额度集合为[{}]", identityNo,newList);
		model.addAttribute("newList", newList);
		return LISTURL;
	}
}

