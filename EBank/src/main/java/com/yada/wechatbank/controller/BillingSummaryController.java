package com.yada.wechatbank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.query.BillingSummaryQuery;
import com.yada.wechatbank.service.BillingSummaryService;

/**
 * 账单摘要控制层
 * 
 * @author liangtieluan
 *
 */
@Controller
@RequestMapping(value = "billingsummary")
public class BillingSummaryController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(BillingSummaryController.class);
	/**
	 * 账单摘要业务层
	 */
	@Autowired
	private BillingSummaryService billingSummaryServiceImpl;
	/**
	 * 账单摘要list页面
	 */
	private static final String LISTURL = "wechatbank_pages/BillingSummary/list";

	/**
	 * 查询账单摘要选择页面
	 * 
	 * @param billingSummaryQuery
	 *            账单摘要Query
	 * @param model
	 *            model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, Model model) {
		// TODO 获取卡列表，登录成功后有方法可以得到卡列表
		List<String> cardList = null;
		logger.info("@WDZD@卡列表cardList[" + cardList + "]");
		// 获取加密后的卡列表，传至页面用
		cardList = billingSummaryServiceImpl.getEncryptCardNOs(cardList);
		// 返回值为空或没有数据
		if (cardList == null) {
			return ERROR;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		} else {
			// 查询账单可选日期
			List<String> dateList = billingSummaryServiceImpl.getDateList();
			model.addAttribute("cardList", cardList);
			model.addAttribute("dateList", dateList);
			model.addAttribute("model", billingSummaryQuery);
		}
		return LISTURL;
	}

	/**
	 * 查询账单摘要
	 * 
	 * @param billingSummaryQuery
	 *            账单摘要Query
	 * @param model
	 *            model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "listP")
	public String listP(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, Model model) {
		// TODO 获取卡列表，登录成功后有方法可以得到卡列表
		List<String> cardList = null;
		// 查询的账单日期
		String date = billingSummaryQuery.getDate();
		// 得到需要查询账单摘要的卡列表
		List<String> queryCardList;
		try {
			// TODO 获取卡列表的方式需要登录成功提供,替换null值
			queryCardList = billingSummaryServiceImpl.getQueryCardList(billingSummaryQuery.getCardNo(), cardList);
		} catch (Exception e) {
			logger.error("@WDZD@cardList crypt error,billingSummaryQuery[" + billingSummaryQuery + "]:" + e);
			return ERROR;
		}
		// 调用行内service 获取账单摘要
		List<BillingSummary> billsList = billingSummaryServiceImpl.getBillingSummaryList(queryCardList, date);
		logger.info("@WDZD@调用行内service根据queryCardList[" + queryCardList + "],date[" + date + "]获取账单摘要,获取到的账单摘要合集billsList[" + billsList + "]");
		// 返回值为空或没有数据
		if (billsList == null) {
			return ERROR;
		}
		// 查询账单可选日期
		model.addAttribute("cardList", cardList);
		model.addAttribute("dateList", billingSummaryServiceImpl.getDateList());
		model.addAttribute("model", billingSummaryQuery);
		model.addAttribute("billsList", billsList);
		return LISTURL;
	}
}
