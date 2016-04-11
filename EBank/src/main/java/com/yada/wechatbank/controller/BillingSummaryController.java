package com.yada.wechatbank.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.query.BillingSummaryQuery;
import com.yada.wechatbank.service.BillingSummaryManager;

/**
 * 账单摘要控制层
 * 
 * @author liangtieluan
 *
 */
@Controller
@RequestMapping(value = "billingsummary")
public class BillingSummaryController {
	private final static Logger logger = LoggerFactory.getLogger(BillingSummaryController.class);
	/**
	 * 账单摘要业务层
	 */
	@Autowired
	private BillingSummaryManager billingSummaryManager;
	/**
	 * 账单摘要list页面
	 */
	private static final String LISTURL = "wechatbank_pages/BillingSummary/list";
	/**
	 * 错误页面
	 */
	private static String ERROR = "wechatbank_pages/error";
	/**
	 * 无信用卡信息跳转URL
	 */
	public static final String NOCARDURL = "wechatbank_pages/nocard";

	/**
	 * 
	 * @param billingSummaryQuery
	 *            账单摘要Query
	 * @param model
	 *            model
	 * @param request
	 *            请求
	 * @return 跳转路径
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, Model model, HttpServletRequest request) {
		String identityNo = (String) request.getAttribute("identityNo");
		// 调用RMI 获取卡片列表（OpenId）
		List<String> cardList = billingSummaryManager.getCardNOs(identityNo);
		logger.info("@WDZD@调用核心根据openId[" + identityNo + "]获取所有卡号,获取的卡列表cardList[" + cardList + "]");
		// RMI返回值为空或没有数据
		if (cardList == null) {
			return ERROR;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		} else {
			// 查询账单可选日期
			List<String> dateList = billingSummaryManager.getDateList();
			model.addAttribute("cardList", cardList);
			model.addAttribute("dateList", dateList);
			model.addAttribute("model", billingSummaryQuery);
		}
		return LISTURL;
	}

	/**
	 * 
	 * @param billingSummaryQuery
	 *            账单摘要Query
	 * @param model
	 *            model
	 * @param request
	 *            请求request
	 * @return 跳转路径
	 */
	@RequestMapping(value = "listP")
	public String listP(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, Model model, HttpServletRequest request) {
		// 查询的账单日期
		String date = billingSummaryQuery.getDate();
		// 得到需要查询账单摘要的卡列表
		List<String> queryCardList;
		try {
			queryCardList = billingSummaryManager.getQueryCardList(billingSummaryQuery.getCardNo(), billingSummaryQuery.getCardNos());
		} catch (Exception e) {
			logger.error("@WDZD@cardList crypt error,billingSummaryQuery[" + billingSummaryQuery + "]:" + e);
			return ERROR;
		}
		// 调用行内service 获取账单摘要
		List<BillingSummary> billsList = billingSummaryManager.getBillingSummaryList(queryCardList, date);
		logger.info("@WDZD@调用核心根据queryCardList[" + queryCardList + "],date[" + date + "]获取账单摘要,获取到的账单摘要合集billsList[" + billsList + "]");
		// RMI返回值为空或没有数据
		if (billsList == null) {
			return ERROR;
		}
		// 查询账单可选日期
		model.addAttribute("cardList", billingSummaryQuery.getCardNos());
		model.addAttribute("dateList", billingSummaryManager.getDateList());
		model.addAttribute("model", billingSummaryQuery);
		model.addAttribute("billsList", billsList);
		return LISTURL;
	}
}
