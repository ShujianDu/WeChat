package com.yada.wechatbank.model;

/**
 * 消费分期（费用试算）(TS011009) 返回实体
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentCost {
	// 卡号
	private String cardNo;
	// 分期金额
	private String installmentAmount;
	// 分期手续费
	private String installmentFee;
	// 分期后每期应还金额-首期
	private String installmentsAlsoAmountFirst;
	// 分期后每期应还金额-后每期
	private String installmentsAlsoAmountEach;
	// 分期手续费收取方式
	private String billFeeMeans;
	// 分期期数
	private String installmentsNumber;
	// 币种
	private String currencyCode;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public String getInstallmentFee() {
		return installmentFee;
	}

	public void setInstallmentFee(String installmentFee) {
		this.installmentFee = installmentFee;
	}

	public String getInstallmentsAlsoAmountFirst() {
		return installmentsAlsoAmountFirst;
	}

	public void setInstallmentsAlsoAmountFirst(String installmentsAlsoAmountFirst) {
		this.installmentsAlsoAmountFirst = installmentsAlsoAmountFirst;
	}

	public String getInstallmentsAlsoAmountEach() {
		return installmentsAlsoAmountEach;
	}

	public void setInstallmentsAlsoAmountEach(String installmentsAlsoAmountEach) {
		this.installmentsAlsoAmountEach = installmentsAlsoAmountEach;
	}

	public String getBillFeeMeans() {
		return billFeeMeans;
	}

	public void setBillFeeMeans(String billFeeMeans) {
		this.billFeeMeans = billFeeMeans;
	}

	public String getInstallmentsNumber() {
		return installmentsNumber;
	}

	public void setInstallmentsNumber(String installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
