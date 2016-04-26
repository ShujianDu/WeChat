package com.yada.wechatbank.controller.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.model.BillingPeriod;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardApply;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;
import com.yada.wechatbank.model.ConsumptionInstallments;
import com.yada.wechatbank.model.ConsumptionInstallmentsesReceive;
import com.yada.wechatbank.model.CreditLimitTemporaryUpReview;
import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.model.HistoryInstallmentList;
import com.yada.wechatbank.model.PointsBalance;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.model.VerificationCardNoResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟行内返回 Created by QinQiang on 2016/4/11.
 */
@Controller
@RequestMapping(value = "mock")
public class MockController {

	private static final String key = "bizResult";

	private final String accountId = "TX01";

	private final String accountNo = "TX0101";

	private final String mobileNo = "13800138000";

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

	@RequestMapping(value = "tempCreditCardReportLost")
	@ResponseBody
	public String tempCreditCardReportLost() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "sendSMS")
	@ResponseBody
	public String sendSMS() {
		Map<String, Object> map = mockResult();
		map.put(key, "true");
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "creditCardReportLost")
	@ResponseBody
	public String creditCardReportLost() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
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
		c.setMobileNo(mobileNo);
		c.setMailBox("123123123123@qq.com");
		c.setFamilyName("李天一");
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
		map.put(key, mobileNo);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getWbicCards")
	@ResponseBody
	public String getWbicCards(HttpServletRequest request){
		JSONObject obj = readReq(request);
		String idNum = (String) obj.get("idNum");
		String idType = (String) obj.get("idType");
		Map<String, Object> map = mockResult();
		if("110625199301280000".equals(idNum) && "IDCD".equals(idType)){
			String cardNo = "111111222222333333";
			map.put(key, cardNo);
		}else{
			return "exception";
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "wbicCardInfoSendSms")
	@ResponseBody
	public String wbicCardInfoSendSms(HttpServletRequest request){
		JSONObject obj = readReq(request);
		String cardNo = (String) obj.get("cardNo");
		Map<String, Object> map = mockResult();
		if("11111111111111111111".equals(cardNo)){
			map.put(key, true);
		}else{
			return "exception";
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getCardBalance")
	@ResponseBody
	public String getCardBalance(HttpServletRequest request){
		JSONObject obj = readReq(request);
		String cardNo = (String) obj.get("cardNo");
		Map<String, Object> map = mockResult();
		if("11111111111111111".equals(cardNo)){
			//返回正常值
			List<Balance> list = new ArrayList<>();

			Balance balance = new Balance();
			balance.setCardNo("11111111111111111");
			balance.setCurrencyCode("CNY");
			balance.setPreCashAdvanceCreditLimit("31");
			balance.setWholeCreditLimit("101");
			balance.setPeriodAvailableCreditLimit("100");

			Balance balance2 = new Balance();
			balance2.setCardNo("11111111111111111");
			balance2.setCurrencyCode("USD");
			balance2.setPreCashAdvanceCreditLimit("33");
			balance2.setWholeCreditLimit("130");
			balance2.setPeriodAvailableCreditLimit("102");
			list.add(balance);
			list.add(balance2);
			map.put(key, list);
		}else if("222222222222222222".equals(cardNo)){
			//返回空值
			List<Balance> list = new ArrayList<>();
			map.put(key, list);
		}else{
			return "exception";
		}
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
		b.setAccountId("BP01");
		b.setCurrencyCode("CNY");
		b.setPeriodStartDate("2016-03-24");
		b.setPeriodEndDate("2016-04-24");
		b.setStatementNo("1");
		BillingPeriod b1 = new BillingPeriod();
		b1.setAccountId("BP02");
		b1.setCurrencyCode("USB");
		b1.setPeriodStartDate("2016-03-24");
		b1.setPeriodEndDate("2016-04-24");
		b1.setStatementNo("2");
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
			b.setClosingBalance("3000");
			b.setCurrencyCode("CNY");
			b.setMinPaymentAmount("800");
			b.setPaymentDueDate("2016-05-09");
			b.setPeriodEndDate("2016-04-24");
			b.setPeriodStartDate("2016-03-24");
		} else {
			b.setClosingBalance("900");
			b.setCurrencyCode("USD");
			b.setMinPaymentAmount("100");
			b.setPaymentDueDate("2016-05-09");
			b.setPeriodEndDate("2016-04-24");
			b.setPeriodStartDate("2016-03-24");
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

	@RequestMapping(value = "getBillingPeriod")
	@ResponseBody
	public String getBillingPeriod() {
		Map<String, Object> map = mockResult();
		List<BillingPeriod> billingPeriods = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			BillingPeriod billingPeriod = new BillingPeriod();
			billingPeriod.setAccountId(accountId);
			billingPeriod.setStatementNo("" + i);
			billingPeriod.setCurrencyCode("CNY");
			billingPeriod.setPeriodStartDate("2016-03-20");
			billingPeriod.setPeriodEndDate("2016-04-20");
			billingPeriods.add(billingPeriod);
		}
		map.put(key, billingPeriods);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getAmountLimit")
	@ResponseBody
	public String getAmountLimit() {
		Map<String, Object> map = mockResult();
		AmountLimit amountLimit = new AmountLimit();
		amountLimit.setAccountId(accountId);
		amountLimit.setAccountNo(accountNo);
		amountLimit.setCurrencyCode("CNY");
		amountLimit.setMaxAmount("10000");
		amountLimit.setMinAmount("1000");
		amountLimit.setRespCode("");
		map.put(key, amountLimit);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "temporaryUpCommit")
	@ResponseBody
	public String temporaryUpCommit() {
		Map<String, Object> map = mockResult();
		map.put(key, true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "creditLimitTemporaryUpReview")
	@ResponseBody
	public String creditLimitTemporaryUpReview() {
		Map<String, Object> map = mockResult();
		CreditLimitTemporaryUpReview cltur = new CreditLimitTemporaryUpReview();
		cltur.setAmount("30000");
		cltur.setCardStyle("1");
		cltur.setCreditLimit("10000");
		cltur.setIssuingBranchId("0000000111");
		cltur.setPmtCreditLimit("10000");
		cltur.setPrincipalResultID("A");
		map.put(key, cltur);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "getTemporaryUpCommitStatus")
	@ResponseBody
	public String getTemporaryUpCommitStatus() {
		Map<String, Object> map = mockResult();
		List<CreditLimitTemporaryUpStatus> list = new ArrayList<>();
		CreditLimitTemporaryUpStatus cltur1 = new CreditLimitTemporaryUpStatus();
		cltur1.setEosEndLimitDate("2016-11-30");
		cltur1.setEosId("060220160425123456");
		cltur1.setEosImpTime("2016-04-24");
		cltur1.setEosLimit("30000");
		cltur1.setEosStarLimitDate("2016-04-25");
		cltur1.setEosState("50");
		list.add(cltur1);
		CreditLimitTemporaryUpStatus cltur2 = new CreditLimitTemporaryUpStatus();
		cltur2.setEosEndLimitDate("2016-11-30");
		cltur2.setEosId("060220160425123456");
		cltur2.setEosImpTime("2016-04-24");
		cltur2.setEosLimit("22000");
		cltur2.setEosStarLimitDate("2016-04-25");
		cltur2.setEosState("60");
		list.add(cltur2);
		CreditLimitTemporaryUpStatus cltur3 = new CreditLimitTemporaryUpStatus();
		cltur3.setEosEndLimitDate("2015-09-30");
		cltur3.setEosId("060220150325123422");
		cltur3.setEosImpTime("2015-04-24");
		cltur3.setEosLimit("12000");
		cltur3.setEosStarLimitDate("2015-03-25");
		cltur3.setEosState("50");
		list.add(cltur3);
		map.put(key, list);
		return JSON.toJSONString(map);
	}

	/**
	 * 消费分期查询
	 * 
	 * @return 可分期消费信息
	 */
	int o = 1;

	@RequestMapping(value = "queryConsumptionInstallments")
	@ResponseBody
	public String queryConsumptionInstallments() {
		Map<String, Object> map = mockResult();
		ConsumptionInstallmentsesReceive consumptionInstallmentsesReceive = new ConsumptionInstallmentsesReceive();
		List<ConsumptionInstallments> consumptionInstallmentsList = new ArrayList<ConsumptionInstallments>();
		if (o == 1) {
			ConsumptionInstallments c = new ConsumptionInstallments();
			c.setAccountedID("01");
			c.setAccountID("02");
			c.setAccountNoID("03");
			c.setCardNo("1111111111111111");
			c.setCurrencyChinaCode("CNY");
			c.setDebitCreditCode("DEBT");
			c.setOriginalCurrencyCode("CNY");
			c.setOriginalTransactionAmount("1111");
			c.setTransactionAmount("1111");
			c.setTransactionDate("2016-04-25");
			c.setTransactionDescription("京东");
			c.setCycleNumber("4");
			c.setTransactionCurrencyCode("CNY");
			c.setTransactionNo("5");
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsesReceive.setConsumptionInstallmentsList(consumptionInstallmentsList);
			consumptionInstallmentsesReceive.setHasNext(true);
			consumptionInstallmentsesReceive.setTransactionNumber("11");
		} else {
			ConsumptionInstallments c1 = new ConsumptionInstallments();
			c1.setAccountedID("01");
			c1.setAccountID("02");
			c1.setAccountNoID("03");
			c1.setCardNo("1111111111111111");
			c1.setCurrencyChinaCode("CNY");
			c1.setDebitCreditCode("DEBT");
			c1.setOriginalCurrencyCode("USD");
			c1.setOriginalTransactionAmount("1111");
			c1.setTransactionAmount("1111");
			c1.setTransactionDate("2016-04-25");
			c1.setTransactionDescription("京东");
			c1.setCycleNumber("4");
			c1.setTransactionCurrencyCode("CNY");
			c1.setTransactionNo("5");
			consumptionInstallmentsList.add(c1);
			consumptionInstallmentsesReceive.setConsumptionInstallmentsList(consumptionInstallmentsList);
			consumptionInstallmentsesReceive.setHasNext(false);
			consumptionInstallmentsesReceive.setTransactionNumber("11");
		}
		o++;
		map.put(key, consumptionInstallmentsesReceive);
		return JSON.toJSONString(map);
	}

	/**
	 * 消费分期试算
	 * 
	 * @return 试算结果
	 */
	@RequestMapping(value = "costConsumptionInstallment")
	@ResponseBody
	private String costConsumptionInstallment() {
		Map<String, Object> map = mockResult();
		ConsumptionInstallmentCost cost = new ConsumptionInstallmentCost();
		cost.setBillFeeMeans("1");
		cost.setCurrencyCode("CNY");
		cost.setInstallmentAmount("1111");
		cost.setInstallmentFee("30");
		cost.setInstallmentsAlsoAmountEach("180");
		cost.setInstallmentsAlsoAmountFirst("170");
		cost.setInstallmentsNumber("6");
		cost.setCardNo("1111111111111111");
		map.put(key, cost);
		return JSON.toJSONString(map);
	}

	/**
	 * 消费分期授权
	 * 
	 * @return 授权结果
	 */
	@RequestMapping(value = "authorizationConsumptionInstallment")
	@ResponseBody
	private String authorizationConsumptionInstallment() {
		Map<String, Object> map = mockResult();
		map.put(key, "+GC00000");
		return JSON.toJSONString(map);
	}

	/**
	 * 附近网点查询
	 * 
	 * @return 重定向地址
	 */
	@RequestMapping(value = "chinabankinfo")
	private String chinabankinfolist() {
		return "redirect:http://localhost/ebank/chinabankinfo/baiduMap.do?longitude=116.326418&latitude=39.984683&userLongitude=116.326417&userLatitude=39.984684&name=%E5%8C%97%E4%BA%AC%E4%B8%B0%E5%8F%B0%E5%88%86%E8%A1%8C";
	}

	private JSONObject readReq(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		String str;
		try {
			while ((str = request.getReader().readLine()) != null) {
                sb.append(str);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject obj = JSON.parseObject(sb.toString());
		return obj;
	}
}
