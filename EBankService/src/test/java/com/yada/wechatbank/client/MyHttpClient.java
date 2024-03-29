package com.yada.wechatbank.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.kafka.MyMessageProducer;
import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.BillCost;
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

/**
 * Created by Echo on 2016/4/27.
 */
public class MyHttpClient extends HttpClient {

	private static final String key = "data";
	private final String getCustMobile = "/getCustMobile.do";
	private final String getBillSendType = "/getBillSendType.do";
	private final String getMobilePhone = "/getMobilePhone.do"; // 获取预约办卡手机号
	private final String getCardInfos = "/getCardInfos.do"; // 获取客户卡列表
	private final String getCardHolderInfo = "/getCardHolderInfo.do"; // 获取客户卡列表
	private final String updateBillSendType = "/updateBillSendType.do"; // 修改账单寄送方式
	private final String getAmountLimit = "/getAmountLimit.do"; // 账单分期上下线
	private final String billInstallment = "/billInstallment.do"; // 账单分期授权
	private final String queryBillCost = "/queryBillCost.do"; // 账单分期试算
	private final String temporaryUpCommit = "/temporaryUpCommit.do"; // 临增额度试算
	private final String creditLimitTemporaryUpReview = "/creditLimitTemporaryUpReview.do"; // 临增额度授权
	private final String getTemporaryUpCommitStatus = "/getTemporaryUpCommitStatus.do"; // 临增额度历史查询
	private final String sendSMS = "/sendSMS.do"; // 发送短信验证码
	private final String getCardBalance = "/getCardBalance.do";// 查询额度
	private final String getWbicCards = "/getWbicCards.do";// 海淘卡查询
	private final String wbicCardInfoSendSms = "/wbicCardInfoSendSms.do";// 海淘卡用户发送短信
	private final String billingPeriod = "/billingPeriod.do";// 查询账期
	private final String billingSummary = "/billingSummary.do";// 查询账单摘要
	private final String alltBillingDetail = "/alltBillingDetail.do";// 已出账单明细
	private final String unsmBillingDetail = "/unsmBillingDetail.do";// 未出账单明细
	private final String getPointsBalance = "/getPointsBalance.do"; // 查询积分余额
	private final String getPointsDetails = "/getPointsDetails.do"; // 查询积分明细
	private final String getPointsValidates = "/getPointsValidates.do"; // 查询积分到期日
	private final String verificationCardNo = "/verificationCardNo.do"; // 积分兑换加密卡号
	private final String verificationPWD = "/verificationPWD.do"; // 验密
	private final String getHistoryInstallment = "/getHistoryInstallment.do"; // 分期历史查询
	private final String cardApply = "/cardApply.do"; // 预约办卡进度查询
	private final String queryConsumptionInstallments = "/queryConsumptionInstallments.do";// 可消费分期信息查询
	private final String costConsumptionInstallment = "/costConsumptionInstallment.do";// 消费分期试算
	private final String authorizationConsumptionInstallment = "/authorizationConsumptionInstallment.do";// 消费分期授权
	private final String tempCreditCardReportLost = "/tempCreditCardReportLost.do";
	private final String creditCardReportLost = "/creditCardReportLost.do";
	private final String relieveCreditCardTempReportLost = "/relieveCreditCardTempReportLost.do";
	protected final String pubilcMobileNo = "18888888888";// 手机号
	protected final String idType = "SSNO";// 证件类型-护照
	protected final String idNo = "MOCK01";// 证件号
	// 测试一卡数据
	protected final String cardNo1 = "6225888899990001"; // 测试卡1
	protected final String cardNo1_Currency1 = "CNY"; // 人民币
	protected final String cardNo1_Currency2 = "USD"; // 美元

	// 测试二卡数据
	protected final String cardNo2 = "6225888899990002"; // 测试卡2
	protected final String cardNo2_Currency1 = "CNY"; // 人民币
	protected final String cardNo2_Currency2 = "JPY"; // 日元

	// 测试三卡数据
	protected final String cardNo3 = "6225888899990003"; // 测试卡3
	protected final String cardNo3_Currency1 = "CNY"; // 人民币
	protected final String cardNo3_Currency2 = "HKD"; // 法郎

	// 测试四卡数据
	protected final String cardNo4 = "6225888899990004"; // 测试卡4
	protected final String cardNo4_Currency1 = "CNY"; // 人民币

	// 卡列表
	public List<String> getCardNoList() {
		List<String> list = new ArrayList<>();
		list.add(cardNo1);
		list.add(cardNo2);
		list.add(cardNo3);
		list.add(cardNo4);
		return list;
	}

	private Map<String, Object> mockResult() {
		Map<String, Object> param = new HashMap<>();
		param.put("returnCode", "00");
		param.put("returnMsg", "成功");
		return param;
	}

	public MyHttpClient(String hostAddr, int conTimeout, int readTimeout,MyMessageProducer myMessageProducer,String title) {
		super(hostAddr, conTimeout, readTimeout,myMessageProducer,title);
	}

	@Override
	public <T> T send(String method, Object object, Class<T> targetClass) {
		Map<String, Object> map = mockResult();
		Map<String, String> requestMap = (HashMap<String, String>) object;
		switch (method) {
		case getCustMobile: {
			getCustMobile(map);
			break;
		}// 查询客户手机号
		case getBillSendType: {
			getBillSendType(requestMap, map);
			break;
		}// 账单寄送方式查询
		case getMobilePhone: {
			getMobilePhone(map);
			break;
		}// 查询客户预约办卡手机号
		case getCardInfos: {
			getCardInfos(requestMap, map);
			break;
		}// 查询客户卡列表
		case getCardHolderInfo: {
			getCardHolderInfo(requestMap, map);
			break;
		}// 客户资料
		case getAmountLimit: {
			getAmountLimit(requestMap, map);
			break;
		}// 账单分期上下线
		case updateBillSendType: {
			updateBillSendType(requestMap, map);
			break;
		}// 修改账单寄送方式
		case billInstallment: {
			billInstallment(requestMap, map);
			break;
		}// 账单分期授权
		case queryBillCost: {
			queryBillCost(requestMap, map);
			break;
		}// 账单分期试算
		case temporaryUpCommit: {
			temporaryUpCommit(requestMap, map);
			break;
		}// 临增额度试算
		case creditLimitTemporaryUpReview: {
			creditLimitTemporaryUpReview(requestMap, map);
			break;
		}// 临增额度授权
		case getTemporaryUpCommitStatus: {
			getTemporaryUpCommitStatus(requestMap, map);
			break;
		}// 临增额度历史查询
		case sendSMS: {
			sendSMS(map);
			break;
		} // 发送短信验证码
		case getCardBalance: {
			getCardBalance(requestMap, map);
			break;
		}// 根据卡号查询额度
		case getWbicCards: {
			getWbicCards(requestMap, map);
			break;
		}// 查询海淘卡
		case wbicCardInfoSendSms: {
			wbicCardInfoSendSms(requestMap, map);
			break;
		}
		// 账期查询
		case billingPeriod: {
			getBillingPeriod(map, object);
			break;
		}
		// 账单摘要
		case billingSummary: {
			getBillingSummary(map, object);
			break;
		}
		// 已出账单明细
		case alltBillingDetail: {
			getBillingDetail(map, object);
			break;
		}
		// 未出账单查询
		case unsmBillingDetail: {
			getBillingDetail(map, object);
			break;
		}
		case getPointsBalance: {
			getPointsBalance(map);
			break;
		}
		case getPointsDetails: {
			getPointsDetails(map);
			break;
		}
		case getPointsValidates: {
			getPointsValidates(map);
			break;
		}
		case verificationCardNo: {
			verificationCardNo(map);
			break;
		}
		case verificationPWD: {
			verificationPWD(map);
			break;
		}
		case getHistoryInstallment: {
			getHistoryInstallment(map);
			break;
		}
		// 预约办卡进度查询
		case cardApply: {
			cardApply(map);
			break;
		}// 可消费分期信息查询
		case queryConsumptionInstallments: {
			getQueryConsumptionInstallments(map, object);
			break;
		}
		// 消费分期试算
		case costConsumptionInstallment: {
			getCstConsumptionInstallment(map, object);
			break;
		}
		// 消费分期授权
		case authorizationConsumptionInstallment: {
			getAuthorizationConsumptionInstallment(map, object);
			break;
		}
		case tempCreditCardReportLost: {
			tempCreditCardReportLost(map);
			break;
		}
		case creditCardReportLost: {
			creditCardReportLost(map);
			break;
		}
		case relieveCreditCardTempReportLost: {
			relieveCreditCardTempReportLost(map);
			break;
		}
		default: {
			break;
		}
		}
		String mapJson = JSON.toJSONString(map);
		T result = JSON.parseObject(mapJson, targetClass);
		return result;
	}

	private void relieveCreditCardTempReportLost(Map<String, Object> map) {
		map.put(key, true);
	}

	private void creditCardReportLost(Map<String, Object> map) {
		map.put(key, true);
	}

	private void tempCreditCardReportLost(Map<String, Object> map) {
		map.put(key, true);
	}

	// 获取预约办卡手机号
	private void getMobilePhone(Map<String, Object> map) {
		map.put(key, "18888888888");
	}

	// 发送短信验证码
	private void sendSMS(Map<String, Object> map) {
		map.put(key, "true");
	}

	// 预约办卡进度查询
	private void cardApply(Map<String, Object> map) {
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
	}

	// 账单寄送方式
	private void getBillSendType(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		if (getCardNoList().contains(reqeustMap.get("cardNo"))) {
			responseMap.put(key, null);
		}
		BillSendType b = new BillSendType();
		b.setBillSendType("C");
		b.setBillSendTypeDesc("测试");
		responseMap.put(key, b);
	}

	// 获取客户手机号
	private void getCustMobile(Map<String, Object> map) {
		map.put(key, pubilcMobileNo);
	}

	// 获取客户卡列表
	private void getCardInfos(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		if (!idType.equals(reqeustMap.get("idType")) || !idNo.equals(reqeustMap.get("idNum"))) {
			responseMap.put(key, null);
		} else {
			List<CardInfo> cardInfoList = new ArrayList<>();
			for (String cardNo : getCardNoList()) {
				CardInfo cardInfo = new CardInfo();
				cardInfo.setCardNo(cardNo);
				cardInfoList.add(cardInfo);
			}
			responseMap.put(key, cardInfoList);
		}
	}

	// 更新客户账单寄送方式
	private void updateBillSendType(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		if (getCardNoList().contains(reqeustMap.get("cardNo"))) {
			responseMap.put(key, null);
		}
		responseMap.put(key, true);
	}

	// 根据卡号查询额度
	private void getCardBalance(Map<String, String> reqeustMap, Map<String, Object> responseMap) {

		String cardNo = reqeustMap.get("cardNo");
		if ("11111111111111111".equals(cardNo)) {
			// 返回正常值
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
			responseMap.put(key, list);
		} else if ("222222222222222222".equals(cardNo)) {
			// 返回空值
			List<Balance> list = new ArrayList<>();
			responseMap.put(key, list);
		} else {
			responseMap.put(key, null);
		}
	}

	// 查询海淘卡
	private void getWbicCards(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		// 获得参数map
		Map<String, String> param = (Map<String, String>) reqeustMap;
		String idNum = param.get("idNum");
		String idType = param.get("idType");
		if ("110625199301280000".equals(idNum) && "IDCD".equals(idType)) {
			String cardNo = "111111222222333333";
			responseMap.put(key, cardNo);
		} else {
			responseMap.put(key, null);
		}
	}

	// 给海淘卡用户发送短信
	private void wbicCardInfoSendSms(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		// 获得参数map
		Map<String, String> param = (Map<String, String>) reqeustMap;
		String cardNo = param.get("cardNo");
		if ("11111111111111111111".equals(cardNo)) {
			responseMap.put(key, true);
		} else {
			responseMap.put(key, null);
		}
	}

	// 查询账单明细
	private void getBillingDetail(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String queryType = param.get("queryType");
		List<BillingDetail> billingDetailList = new ArrayList<>();
		if ("ALLT".equals(queryType)) {
			BillingDetail b1 = new BillingDetail();
			b1.setCardNo(cardNo1);
			b1.setCurrencyCode("CNY");
			b1.setDebitCreditCode("DEBT");
			b1.setReturnMsg("成功");
			b1.setStartnum("1");
			b1.setTransactionAmount("1234");
			b1.setTransactionDate("2016-04-16");
			b1.setTransactionDescription("京东商城");
			billingDetailList.add(b1);
			map.put(key, billingDetailList);
		} else if ("UNSM".equals(queryType)) {
			BillingDetail b = new BillingDetail();
			b.setCardNo(cardNo1);
			b.setCurrencyCode("USD");
			b.setDebitCreditCode("DEBT");
			b.setReturnMsg("成功");
			b.setStartnum("1");
			b.setTransactionAmount("1234");
			b.setTransactionDate("2016-04-16");
			b.setTransactionDescription("京东商城");
			billingDetailList.add(b);
			BillingDetail b1 = new BillingDetail();
			b1.setCardNo(cardNo1);
			b1.setCurrencyCode("USD");
			b1.setDebitCreditCode("DEBT");
			b1.setReturnMsg("成功");
			b1.setStartnum("1");
			b1.setTransactionAmount("1234");
			b1.setTransactionDate("2016-04-16");
			b1.setTransactionDescription("京东商城");
			billingDetailList.add(b1);
			map.put(key, billingDetailList);
		} else {

		}

	}

	public void getPointsBalance(Map<String, Object> map) {
		PointsBalance pointsBalance = new PointsBalance();
		pointsBalance.setTotalPoint("1212");
		map.put(key, pointsBalance);
	}

	public void getPointsDetails(Map<String, Object> map) {
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
	}

	public void getPointsValidates(Map<String, Object> map) {
		List<PointsValidates> pointsValidatesList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			PointsValidates pointsValidates = new PointsValidates();
			pointsValidates.setCardNo("111111111111111111" + i);
			pointsValidates.setProductName("111111111111111" + i);
			pointsValidates.setProductCode("1111111111111111" + i);
			pointsValidatesList.add(pointsValidates);
		}
		map.put(key, pointsValidatesList);
	}

	public void verificationCardNo(Map<String, Object> map) {
		VerificationCardNoResult verificationCardNoResult = new VerificationCardNoResult();
		verificationCardNoResult.setEncryptCardNo("11111111111111111111111");
		verificationCardNoResult.setSign("2222222222222222222");
		map.put(key, verificationCardNoResult);
	}

	private void verificationPWD(Map<String, Object> map) {
		map.put(key, true);
	}

	private void getHistoryInstallment(Map<String, Object> map) {
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
		historyInstallmentList.setEntityList(historyInstallments);
		historyInstallmentList.setFollowUp(true);
		historyInstallmentList.setTransactionNumber("10");
		map.put(key, historyInstallmentList);
	}

	// 可消费分期信息查询
	private void getQueryConsumptionInstallments(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String currencyCode = param.get("currencyCode");
		if ("CNY".equals(currencyCode)) {
			ConsumptionInstallmentsesReceive consumptionInstallmentsesReceive = new ConsumptionInstallmentsesReceive();
			List<ConsumptionInstallments> consumptionInstallmentsList = new ArrayList<ConsumptionInstallments>();
			ConsumptionInstallments c = new ConsumptionInstallments();
			c.setAccountedID("01");
			c.setAccountID("02");
			c.setAccountNoID("03");
			c.setCardNo(cardNo1);
			c.setDebitCreditCode("DEBT");
			c.setOriginalCurrencyCode("CNY");
			c.setOriginalTransactionAmount("+00000000000800010");
			c.setTransactionAmount("+00000000000800010");
			c.setTransactionDate("2016-04-25");
			c.setTransactionDescription("京东");
			c.setCycleNumber("4");
			c.setTransactionCurrencyCode("CNY");
			c.setTransactionNo("5");
			consumptionInstallmentsList.add(c);
			consumptionInstallmentsesReceive.setGcsConsumptionInstallmentsEntitys(consumptionInstallmentsList);
			;
			consumptionInstallmentsesReceive.setTransactionNumber("1");
			consumptionInstallmentsesReceive.setHasNext(false);
			map.put(key, consumptionInstallmentsesReceive);
		} else {
			// 其他币种无信息
		}
	}

	// 消费分期试算
	private void getCstConsumptionInstallment(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String currencyCode = param.get("currencyCode");
		if ("CNY".equals(currencyCode)) {
			ConsumptionInstallmentCost cost = new ConsumptionInstallmentCost();
			cost.setBillFeeMeans("1");
			cost.setCurrencyCode("CNY");
			cost.setInstallmentAmount("+00000000000800010");
			cost.setInstallmentFee("30");
			cost.setInstallmentsAlsoAmountEach("180");
			cost.setInstallmentsAlsoAmountFirst("170");
			cost.setInstallmentsNumber("6");
			cost.setCardNo(cardNo1);
			map.put(key, cost);
		} else {
			// 其他币种无返回null
		}
	}

	// 消费分期授权
	private void getAuthorizationConsumptionInstallment(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String currencyCode = param.get("currencyCode");
		if ("CNY".equals(currencyCode)) {
			map.put(key, "+GC00000");
		} else if ("USD".equals(currencyCode)) {
			map.put(key, "+GC00001");
		} else {
			// 其他币种无返回null
		}
	}

	// 客户资料
	private void getCardHolderInfo(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		if (!getCardNoList().contains(reqeustMap.get("cardNo"))) {
			responseMap.put(key, null);
		}
		CardHolderInfo c = new CardHolderInfo();
		c.setMobileNo(pubilcMobileNo);
		c.setMailBox("123123123123@qq.com");
		c.setFamilyName("李天一");
		c.setGender("Male");
		c.setPostalCode("100010");
		c.setHomeAddressPhone("67986543");
		c.setWorkUnitName("北京亚大通讯");
		c.setWorkUnitPhone("82971902");
		c.setBillAddressLine("上地西路8号院B座");
		responseMap.put(key, c);
	}

	// 查询账期
	private void getBillingPeriod(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String cardNo = param.get("cardNo");
		// 根据卡号返回的账期不同
		if (cardNo1.equals(cardNo)) {
			// 正常返回账期
			List<BillingPeriod> list = new ArrayList<>();
			BillingPeriod b = new BillingPeriod();
			b.setAccountId("BP01");
			b.setCurrencyCode(cardNo1_Currency1);
			b.setPeriodStartDate("2016-03-24");
			b.setPeriodEndDate("2016-04-24");
			b.setStatementNo("1");
			BillingPeriod b1 = new BillingPeriod();
			b1.setAccountId("BP02");
			b1.setCurrencyCode(cardNo1_Currency2);
			b1.setPeriodStartDate("2016-03-24");
			b1.setPeriodEndDate("2016-04-24");
			b1.setStatementNo("2");
			list.add(b);
			list.add(b1);
			map.put(key, list);
		} else if (cardNo2.equals(cardNo)) {
			// 无账期
			List<BillingPeriod> list = new ArrayList<>();
			map.put(key, list);
		} else {
			// 无返回，bizResult为null
		}
	}

	// 根据账期查询账单摘要
	public void getBillingSummary(Map<String, Object> map, Object object) {
		Map<String, String> param = (Map<String, String>) object;
		String statementNo = param.get("statementNo");
		BillingSummary b = new BillingSummary();
		if ("1".equals(statementNo)) {
			b.setClosingBalance("3000");
			b.setCurrencyCode(cardNo1_Currency1);
			b.setMinPaymentAmount("800");
			b.setPaymentDueDate("2016-05-09");
			b.setPeriodEndDate("2016-04-24");
			b.setPeriodStartDate("2016-03-24");
			map.put(key, b);
		} else if ("2".equals(statementNo)) {
		}
	}

	// 账单分期上下线
	private void getAmountLimit(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		if (!getCardNoList().contains(reqeustMap.get("cardNo")) || !"CNY".equals(reqeustMap.get("currencyCode"))) {
			responseMap.put(key, null);
		}
		AmountLimit amountLimit = new AmountLimit();
		amountLimit.setAccountId("MOCK01AD");
		amountLimit.setAccountNo("MOCK01AN");
		amountLimit.setCurrencyCode("CNY");
		amountLimit.setMaxAmount("10000");
		amountLimit.setMinAmount("1000");
		amountLimit.setRespCode("");
		responseMap.put(key, amountLimit);
	}

	// 账单分期试算
	private void queryBillCost(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		BillCost b = new BillCost();
		b.setBillFeeMeans("1");
		b.setCurrentBillMinimum("200.00");
		b.setCurrentBillSurplusAmount("123.00");
		b.setInstallmentsAlsoAmountEach("100.00");
		b.setInstallmentsAlsoAmountFirst("3.00");
		b.setInstallmentsfee("1.00");
		b.setInstallmentsNumber("3");
		responseMap.put(key, b);
	}

	// 账单分期授权
	private void billInstallment(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		responseMap.put(key, "+GC00000");
	}

	// 账单分期授权
	private void temporaryUpCommit(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		responseMap.put(key, true);
	}

	// 账单分期授权
	private void creditLimitTemporaryUpReview(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		CreditLimitTemporaryUpReview cltur = new CreditLimitTemporaryUpReview();
		cltur.setAmount("30000");
		cltur.setCardStyle("1");
		cltur.setCreditLimit("10000");
		cltur.setIssuingBranchId("0000000111");
		cltur.setPmtCreditLimit("10000");
		cltur.setPrincipalResultID("A");
		responseMap.put(key, cltur);
	}

	// 账单分期授权
	private void getTemporaryUpCommitStatus(Map<String, String> reqeustMap, Map<String, Object> responseMap) {
		List<CreditLimitTemporaryUpStatus> list = new ArrayList<>();
		CreditLimitTemporaryUpStatus cltur1 = new CreditLimitTemporaryUpStatus();
		cltur1.setEosEndLimitDate("2016-11-30");
		cltur1.setEosId("060220160425123456");
		cltur1.setEosImpTime("2016-04-24");
		cltur1.setEosLimit("30000");
		cltur1.setEosStarLimitDate("2016-04-25");
		cltur1.setEosState("60");
		list.add(cltur1);
		CreditLimitTemporaryUpStatus cltur2 = new CreditLimitTemporaryUpStatus();
		cltur2.setEosEndLimitDate("2016-11-30");
		cltur2.setEosId("060220160425123456");
		cltur2.setEosImpTime("2016-04-24");
		cltur2.setEosLimit("22000");
		cltur2.setEosStarLimitDate("2016-04-25");
		cltur2.setEosState("50");
		list.add(cltur2);
		CreditLimitTemporaryUpStatus cltur3 = new CreditLimitTemporaryUpStatus();
		cltur3.setEosEndLimitDate("2015-09-30");
		cltur3.setEosId("060220150325123422");
		cltur3.setEosImpTime("2015-04-24");
		cltur3.setEosLimit("12000");
		cltur3.setEosStarLimitDate("2015-03-25");
		cltur3.setEosState("50");
		list.add(cltur3);
		responseMap.put(key, list);
	}
}
