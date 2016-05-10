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
	public String list(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, HttpServletRequest request, Model model) {
		request.getSession().setAttribute("menuId", "1");
		String identityNo = getIdentityNo(request);
		String identityType = getIdentityType(request);
		logger.info("@BillingSummary@get cardList by identityNo[" + identityNo + "]and identityType[" + identityType + "]");
		// 获取加密后的卡列表，传至页面用
		List<String> cardList = billingSummaryServiceImpl.selectCardNoList(identityType, identityNo);
		logger.info("@BillingSummary@get cardList by identityNo[" + identityNo + "],cardList[" + cardList + "]");
		if (cardList == null) {
			return BUSYURL;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		} else {
			try {
				Crypt.cardNoCrypt(cardList);
			} catch (Exception e) {
				logger.error("@BillingSummary@ cardNoCrypt error,cardList[" + identityNo + "]and identityType[" + identityType + "],cardList[" + cardList + "]");
				return BUSYURL;
			}
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
	@RequestMapping(value = "getBillingSummary")
	@ResponseBody
	public String getBillingSummary(String cardNo, String date) {
		List<BillingSummary> billingSummaries;
		try {
			cardNo = Crypt.decode(cardNo);
		} catch (Exception e1) {
			logger.error("@BillingSummary@ cardNo decode error,cardNo[" + cardNo + "]");
			return JSONObject.toJSONString(null);
		}
		logger.info("@BillingSummary@ajax getBillingSummary,cardNo[" + cardNo + "],date[" + date + "]");
		// 调用行内service 获取账单摘要
		billingSummaries = billingSummaryServiceImpl.getBillingSummaryList(cardNo, date);
		logger.info("@BillingSummary@ajax getBillingSummary,cardNo[" + cardNo + "],date[" + date + "],billingSummaries[" + billingSummaries + "]");
		// 没有查询到账单，如果查询出错，直接返回null
		if (billingSummaries != null && billingSummaries.size() == 0) {
			return JSONObject.toJSONString("");
		}
		return JSONObject.toJSONString(billingSummaries);
	}
}
