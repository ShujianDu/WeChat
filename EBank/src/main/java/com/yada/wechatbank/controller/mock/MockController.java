package com.yada.wechatbank.controller.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.model.BillingPeriod;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardApply;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.model.HistoryInstallmentList;
import com.yada.wechatbank.model.PointsBalance;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.model.VerificationCardNoResult;

/**
 * 模拟行内返回 Created by QinQiang on 2016/4/11.
 */
@Controller
@RequestMapping(value = "mock")
public class MockController {

	private static final String key = "bizResult";

	private Map<String, Object> mockResult() {
		Map<String, Object> param = new HashMap<>();
		param.put("returnCode", "00");
		param.put("returnMsg", "成功");
		return param;
	}

	@RequestMapping(value = "getMobilePhone")
	@ResponseBody
	public String getMobilePhone() {
		Map<String, Object> map = mockResult();
		map.put(key, "18888888888");
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "sendSMS")
	@ResponseBody
	public String sendSMS() {
		Map<String, Object> map = mockResult();
		map.put(key, "true");
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "cardApply")
	@ResponseBody
	public String cardApply() {
		Map<String, Object> map = mockResult();
		CardApplyList list = new CardApplyList();
		list.setHasNext(true);
		List<CardApply> cardApplies = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			CardApply cardApply = new CardApply();
			cardApply.setApplyNo("No:" + i);
			cardApply.setPdnDes("PdnDes" + i);
			cardApply.setPassDate("tPassDate" + i);
			cardApply.setPhaseDesc("Desc" + i);
			cardApplies.add(cardApply);
		}
		list.setCardApplies(cardApplies);
		map.put(key, list);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "tempCreditCardReportLost")
	@ResponseBody
	public String tempCreditCardReportLost() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "creditCardReportLost")
	@ResponseBody
	public String creditCardReportLost() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "relieveCreditCardTempReportLost")
	@ResponseBody
	public String relieveCreditCardTempReportLost() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getCardInfos")
	@ResponseBody
	public String getCardInfos() {
		Map<String, Object> map = mockResult();
		List<CardInfo> cardInfoList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			CardInfo cardInfo = new CardInfo();
			cardInfo.setCardNo("622588014852929" + i);
			cardInfoList.add(cardInfo);
		}
		map.put(key, cardInfoList);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getBillSendType")
	@ResponseBody
	public String getBillSendType() {
		Map<String, Object> map = mockResult();
		BillSendType b = new BillSendType();
		b.setBillSendType("C");
		b.setBillSendTypeDesc("测试");
		map.put(key, b);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "updateBillSendType")
	@ResponseBody
	public String updateBillSendType() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getCardHolderInfo")
	@ResponseBody
	public String getCardHolderInfo() {
		Map<String, Object> map = mockResult();
		CardHolderInfo c = new CardHolderInfo();
		c.setMobileNo("1591111111");
		c.setMailBox("123123123123@qq.com");
		c.setFamilyName("李任日");
		c.setGender("Male");
		c.setPostalCode("100010");
		c.setHomeAddressPhone("67986543");
		c.setWorkUnitName("北京亚大通讯");
		c.setWorkUnitPhone("82971902");
		c.setBillAddressLine("上地西路8号院B座");
		map.put(key, c);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getHistoryInstallment")
	@ResponseBody
	public String queryHistoryInstallment() {
		Map<String, Object> map = mockResult();
		HistoryInstallmentList historyInstallmentList = new HistoryInstallmentList();
		List<HistoryInstallment> historyInstallments = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			HistoryInstallment historyInstallment = new HistoryInstallment();
			historyInstallment.setCardNo("11111111111111111" + i);
			historyInstallment.setInstalmentCompleteDate("1111" + i);
			historyInstallment.setInstalmentNextPostingAmount("1111" + i);
			historyInstallment.setInstalmentOriginalAmount("1111" + i);
			historyInstallments.add(historyInstallment);
		}
		historyInstallmentList.setHistoryInstallmentList(historyInstallments);
		historyInstallmentList.setFollowUp(true);
		historyInstallmentList.setTransactionNumber("10");
		map.put(key, historyInstallmentList);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getPointsBalance")
	@ResponseBody
	public String getPointsBlance() {
		Map<String, Object> map = mockResult();
		PointsBalance pointsBalance = new PointsBalance();
		pointsBalance.setTotalPoint("1212");
		map.put(key, pointsBalance);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getPointsDetails")
	@ResponseBody
	public String getPointsDetail() {
		Map<String, Object> map = mockResult();
		List<PointsDetail> pointsDetailList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			PointsDetail pointsDetail = new PointsDetail();
			pointsDetail.setId(String.valueOf(i));
			pointsDetail.setCardNo("1111111111111111" + i);
			if (i == 2) {
				pointsDetail.setParentId("1");
			}
			pointsDetail.setPointuseFlg("正常");
			pointsDetail.setProductName("1111" + i);
			pointsDetailList.add(pointsDetail);
		}
		map.put(key, pointsDetailList);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getPointsValidates")
	@ResponseBody
	public String getPointsValidates() {
		Map<String, Object> map = mockResult();
		List<PointsValidates> pointsValidatesList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			PointsValidates pointsValidates = new PointsValidates();
			pointsValidates.setCardNo("111111111111111111" + i);
			pointsValidates.setProductName("111111111111111" + i);
			pointsValidates.setProductCode("1111111111111111" + i);
			pointsValidatesList.add(pointsValidates);
		}
		map.put(key, pointsValidatesList);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "verificationCardNo")
	@ResponseBody
	public String verificationCardNo() {
		Map<String, Object> map = mockResult();
		VerificationCardNoResult verificationCardNoResult = new VerificationCardNoResult();
		verificationCardNoResult.setEncryptCardNo("11111111111111111111111");
		verificationCardNoResult.setSign("2222222222222222222");
		map.put(key, verificationCardNoResult);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "verificationPWD")
	@ResponseBody
	public String verificationPWD() {
		Map<String, Object> map = mockResult();
		String res = "true";
		map.put(key, res);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getCustMobile")
	@ResponseBody
	public String getCustMobile() {
		Map<String, Object> map = mockResult();
		String mobileNo = "11111111111";
		map.put(key, mobileNo);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getCurrentPeriodBillMethod")
	@ResponseBody
	public String getCurrentPeriodBillMethod() {
		Map<String, Object> map = mockResult();
		List<BillingPeriod> billingPeriods = new ArrayList<>();
		BillingPeriod b = new BillingPeriod();
		b.setAccountId("123123");
		b.setStatementNo("512");
		b.setPeriodEndDate("2016-04-20");
		billingPeriods.add(b);
		map.put(key, billingPeriods);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getWbicCards")
	@ResponseBody
	public String getWbicCards() {
		Map<String, Object> map = mockResult();
		String cardNo = "111111222222333333";
		map.put(key, cardNo);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "wbicCardInfoSendSms")
	@ResponseBody
	public String wbicCardInfoSendSms() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getBalance")
	@ResponseBody
	public String getBalance() {
		Map<String, Object> map = mockResult();
		List<Balance> list = new ArrayList<>();

		Balance balance = new Balance();
		balance.setCardNo("11111111111111111");
		balance.setCurrencyCode("CNY");
		balance.setPreCashAdvanceCreditLimit("31");
		balance.setWholeCreditLimit("101");
		balance.setPeriodAvailableCreditLimit("100");

		Balance balance2 = new Balance();
		balance2.setCardNo("11111111111112223");
		balance2.setCurrencyCode("USD");
		balance2.setPreCashAdvanceCreditLimit("33");
		balance2.setWholeCreditLimit("130");
		balance2.setPeriodAvailableCreditLimit("102");
		list.add(balance);
		list.add(balance2);
		map.put(key, list);
		return JSON.toJSONString(map);
	}

	/**
	 * 账期查询
	 * 
	 * @return 账期
	 */
	@RequestMapping(value = "billingPeriod")
	@ResponseBody
	public String billingPeriod() {
		Map<String, Object> map = mockResult();
		List<BillingPeriod> list = new ArrayList<>();
		BillingPeriod b = new BillingPeriod();
		b.setAccountId("1");
		b.setCurrencyCode("CNY");
		b.setPeriodStartDate("2016-04-01");
		b.setPeriodEndDate("2016-04-30");
		b.setStatementNo("1");
		BillingPeriod b1 = new BillingPeriod();
		b1.setAccountId("1");
		b1.setCurrencyCode("CNY");
		b1.setPeriodStartDate("2016-04-01");
		b1.setPeriodEndDate("2016-04-30");
		b1.setStatementNo("1");
		list.add(b);
		list.add(b1);
		map.put(key, list);
		return JSON.toJSONString(map);
	}

	/**
	 * 账单摘要查询
	 * 
	 * @return 账单摘要
	 */
	// 查询双币种账单，需要查询两次，i来模拟双币种
	int i = 1;

	@RequestMapping(value = "billingSummary")
	@ResponseBody
	public String billingSummary() {
		Map<String, Object> map = mockResult();
		BillingSummary b = new BillingSummary();
		if (i % 2 != 0) {
			b.setCardNo("1111111111111111");
			b.setClosingBalance("4444");
			b.setCurrencyCode("CNY");
			b.setMinPaymentAmount("333");
			b.setPaymentDueDate("444");
			b.setPeriodEndDate("20160417");
			b.setPeriodStartDate("20160416");
		} else {
			b.setCardNo("1111111111111111");
			b.setClosingBalance("66666");
			b.setCurrencyCode("USD");
			b.setMinPaymentAmount("666");
			b.setPaymentDueDate("666");
			b.setPeriodEndDate("20160417");
			b.setPeriodStartDate("20160416");
		}
		i++;
		map.put(key, b);
		return JSON.toJSONString(map);
	}

	/**
	 * 已出账单明细查询
	 * 
	 * @return 账单明细
	 */
	// m为1时，可以查询更多账单，大于一时不能查询
	int m = 1;

	@RequestMapping(value = "alltBillingDetail")
	@ResponseBody
	public String alltBillingDetail() {
		Map<String, Object> map = mockResult();
		List<BillingDetail> billingDetailList = new ArrayList<>();
		if (m != 1) {
			BillingDetail b1 = new BillingDetail();
			b1.setCardNo("1111111111111111");
			b1.setCurrencyCode("CNY");
			b1.setDebitCreditCode("DEBT");
			b1.setReturnMsg("成功");
			b1.setStartnum("1");
			b1.setTransactionAmount("1234");
			b1.setTransactionDate("2016-04-16");
			b1.setTransactionDescription("京东商城");
			billingDetailList.add(b1);
		} else {
			BillingDetail b = new BillingDetail();
			b.setCardNo("1111111111111111");
			b.setCurrencyCode("CNY");
			b.setDebitCreditCode("DEBT");
			b.setReturnMsg("成功");
			b.setStartnum("1");
			b.setTransactionAmount("1234");
			b.setTransactionDate("2016-04-16");
			b.setTransactionDescription("京东商城");
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
		}
		m++;
		map.put(key, billingDetailList);
		return JSON.toJSONString(map);
	}

	/**
	 * 未出账单明细查询
	 * 
	 * @return 账单明细
	 */
	// m为1时，可以查询更多账单，大于一时不能查询
	int n = 1;

	@RequestMapping(value = "unsmBillingDetail")
	@ResponseBody
	public String unsmBillingDetail() {
		Map<String, Object> map = mockResult();
		List<BillingDetail> billingDetailList = new ArrayList<>();
		if (n != 1) {
			BillingDetail b1 = new BillingDetail();
			b1.setCardNo("1111111111111111");
			b1.setCurrencyCode("CNY");
			b1.setDebitCreditCode("DEBT");
			b1.setReturnMsg("成功");
			b1.setStartnum("1");
			b1.setTransactionAmount("1234");
			b1.setTransactionDate("2016-04-16");
			b1.setTransactionDescription("京东商城");
			billingDetailList.add(b1);
		} else {
			BillingDetail b = new BillingDetail();
			b.setCardNo("1111111111111111");
			b.setCurrencyCode("CNY");
			b.setDebitCreditCode("DEBT");
			b.setReturnMsg("成功");
			b.setStartnum("1");
			b.setTransactionAmount("1234");
			b.setTransactionDate("2016-04-16");
			b.setTransactionDescription("京东商城");
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
			billingDetailList.add(b);
		}
		n++;
		map.put(key, billingDetailList);
		return JSON.toJSONString(map);
	}
}
