package com.yada.wechatbank.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.query.BillingSummaryQuery;
import com.yada.wechatbank.service.BillingSummaryService;
import com.yada.wechatbank.util.Crypt;

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
	// 账单摘要list页面
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
		List<String> cardList = new ArrayList<>();
		cardList.add("1111111111111111");
		cardList.add("2222222222222222");
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
	 * 动态获取账单摘要信息
	 * 
	 * @param cardNo
	 *            卡号
	 * @param date
	 *            账单日期
	 * @return 查询结果
	 */
	@RequestMapping(value = "getBillingSummary", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBillingSummary(String cardNo, String date) {
		List<BillingSummary> billingSummaries;
		try {
			cardNo = Crypt.decode(cardNo);
			// 调用行内service 获取账单摘要
			billingSummaries = billingSummaryServiceImpl.getBillingSummaryList(cardNo, date);
		} catch (Exception e) {
			// 解密或解密失败
			logger.error("@WDZD@调用行内service根据queryCardList[" + cardNo + "],date[" + date + "]获取账单摘要,获取到的账单摘要合集billsList[" + null + "]");
			// 返回null直接跳错误页面
			return JSONObject.toJSONString(null);
		}
		// 没有查询到账单，如果查询出错，直接返回null
		if (billingSummaries != null && billingSummaries.size() == 0) {
			return JSONObject.toJSONString("");
		}
		return JSONObject.toJSONString(billingSummaries);
	}
}
