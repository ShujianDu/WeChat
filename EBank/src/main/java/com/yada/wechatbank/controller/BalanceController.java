package com.yada.wechatbank.controller;

import com.alibaba.fastjson.JSONObject;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.query.BalanceQuery;
import com.yada.wechatbank.service.BalanceService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 我的额度
 * @author tx
 */
@Controller
@RequestMapping(value = "balance")
public class BalanceController extends BaseController{
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());

	@Autowired
	private BalanceService balanceServiceImpl;

	public static final String LISTURL = "wechatbank_pages/Balance/list";

	/**
	 * 额度查询
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request,@ModelAttribute("formBean") BalanceQuery balanceQuery, Model model) {
		request.getSession().setAttribute("menuId", "2");
		String identityNo = getIdentityNo(request);
		String identityType = getGcsIdentityType(request);
		List<CardInfo> cardList = balanceServiceImpl.getProessCardNoList(identityType, identityNo);
		if (cardList == null) {
			logger.info("@Balance根据证件类型[{}]证件号[{}]获取到的卡列表为null", identityType, identityNo);
			return BUSYURL;
		} else if (cardList.size() == 0) {
			logger.info("@Balance根据证件类型[{}]证件号[{}]获取到的卡列表长度为0", identityType, identityNo);
			return NOCARDURL;
		} else {
			logger.info("@Balance根据证件类型[{}]证件号[{}]获取卡片列表,获取到的卡列表cardList[{}]", identityType, identityNo, cardList);
			model.addAttribute("cardList", cardList);
			return LISTURL;
		}
	}
	/**
	 * 额度查询获取单卡数据
	 * @param cardNo        卡号
	 */
	@RequestMapping(value = "getCardNoBalance_Ajax")
	@ResponseBody
	public String getCardNoBalance_Ajax(String cardNo) {
		String decodeCardNo;
		try {
			cardNo = Crypt.decode(cardNo);
		} catch (Exception e) {
			logger.info("@Balance解密cardNo[{}]失败", cardNo);
			return JSONObject.toJSONString("exception");
		}
		List<Balance> newList = balanceServiceImpl.getCardNoBalance(cardNo);
		logger.info("@Balance通过cardNo[{}]获取到的额度集合为[{}]", cardNo, newList);
		if (newList == null) {
			return JSONObject.toJSONString("exception");
		}
		return JSONObject.toJSONString(newList);
	}
}

