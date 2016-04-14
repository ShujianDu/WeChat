package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.query.BillingSummaryQuery;
import com.yada.wechatbank.service.BillingDetailService;
import com.yada.wechatbank.util.Crypt;

/**
 * 账单明细
 * 
 * @author liangtieluan
 *
 */
@Controller
@RequestMapping(value = "billingdetail")
public class BillingDetailController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(BillingDetailController.class);
	@Autowired
	private BillingDetailService billingDetailServiceImpl;
	// 账单明细路径
	private static final String LISTURL = "wechatbank_pages/BillingDetail/list";
	// 查询开始条数
	private static final String STARTNUM = "1";
	// 一次查询账单总条数
	private static final String TOTALNUM = "10";
	// 一页显示账单条数
	private static final int ONEPAGE = 10;

	/**
	 * 账单明细查询
	 * 
	 * @param billingSummaryQuery
	 *            账单摘要查询实体
	 * @param request
	 *            请求
	 * @param model
	 *            model
	 * @return 跳转地址
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") BillingSummaryQuery billingSummaryQuery, HttpServletRequest request, Model model) {
		// 查询类型
		String queryType = billingSummaryQuery.getBillType();
		// 卡号
		String cardNo = billingSummaryQuery.getCardNo();
		model.addAttribute("cardNo", cardNo);
		try {
			cardNo = Crypt.decode(billingSummaryQuery.getCardNo());
		} catch (Exception e) {
			logger.info("@WDZD@cardNo crypt error,cardNo[" + cardNo + "]:" + e);
			return ERROR;
		}
		// 币种
		String currencyCode = "";
		// 账单开始日期
		String periodStartDate = "";
		// 账单结束日期
		String periodEndDate = "";
		// 已出账单需要下列参数，未出账单不用下列参数
		if ("ALLT".equals(queryType)) {
			currencyCode = billingSummaryQuery.getCurrencyCode();
			periodStartDate = billingSummaryQuery.getPeriodStartDate();
			periodEndDate = billingSummaryQuery.getPeriodEndDate();
		}
		// 获得账单明细
		List<BillingDetail> billingDetailList = billingDetailServiceImpl.getBillingDetail(cardNo, queryType, STARTNUM, TOTALNUM, periodStartDate,
				periodEndDate, currencyCode);
		// 返回值为空或没有数据
		if (billingDetailList == null) {
			return BUSYURL;
		}
		model.addAttribute("billingDetailList", billingDetailList);
		model.addAttribute("startnum", STARTNUM + 1);
		model.addAttribute("model", billingSummaryQuery);
		model.addAttribute("cardNoShow", cardNo.subSequence(0, 4) + "********" + cardNo.substring(cardNo.length() - 4, cardNo.length()));
		return LISTURL;
	}

	/**
	 * 查询更多账单明细
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param billingDetail
	 *            账单明细实体
	 * @throws IOException
	 *             IO异常
	 */
	@RequestMapping(value = "getMore_ajax")
	public void getMore_ajax(HttpServletRequest request, HttpServletResponse response, BillingDetail billingDetail) throws IOException {
		request.setCharacterEncoding("utf-8");
		// 返回结果
		String result = "";
		// 账单明细集合
		List<BillingDetail> billingDetailList = new ArrayList<BillingDetail>();
		// 卡号,需要先解密
		String cardNo = "";
		try {
			cardNo = Crypt.decode(request.getParameter("cardNo"));
		} catch (Exception e) {
			logger.error("@WDZD@cardNo crypt error,cardNo[" + cardNo + "]:" + e);
		}
		// 币种
		String currencyCode = request.getParameter("currencyCode");
		// 账单开始日期
		String periodStartDate = request.getParameter("periodStartDate");
		// 账单结束日期
		String periodEndDate = request.getParameter("periodEndDate");
		// 账单类型
		String queryType = request.getParameter("queryType");
		// 查询开始条数
		String startnum = request.getParameter("startnum");
		// 查询账单，从开始条数开始查询
		billingDetailList = billingDetailServiceImpl.getBillingDetail(cardNo, queryType, startnum, TOTALNUM, periodStartDate, periodEndDate, currencyCode);
		// 获得当前账单明细的展示数目
		if (billingDetailList == null) {
			result = "null";
		} else if (billingDetailList.size() == ONEPAGE) {
			// 设置开始条数
			billingDetailList.get(0).setStartnum(Integer.valueOf(startnum) + ONEPAGE + "");
		}
		// 将账单明细转换为json格式
		result = JSONArray.fromObject(billingDetailList).toString();
		if ("".equals(result) || result == null) {
			result = "null";
		}
		logger.info("@WDZD@get more billingdetail----------the next Page (ajax)----------return result[" + result + "]");
		response.getWriter().print(result);
		response.getWriter().flush();
	}
}
