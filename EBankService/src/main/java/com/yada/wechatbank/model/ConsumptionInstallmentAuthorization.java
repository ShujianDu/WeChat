package com.yada.wechatbank.model;

/**
 * 消费分期（费用试算）(TS011009)及消费分期授权(TS011011)上送报文实体
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentAuthorization {
	// 帐户键值1--原消费中账户ID。请使用TS011007查询后的“accountID”域值
	private String accountKeyOne;
	// 帐户键值2--原消费中入账账户ID。请使用TS011007查询后的“accountedID”域值
	private String accountKeyTwo;
	// 币种--原消费币种
	private String currencyCode;
	// 帐期号--原消费帐期号
	private String billDateNo;
	// 交易金额
	private String transactionAmount;
	// 卡号
	private String cardNo;
	// 账号id。请使用TS011007查询后的“accountNoID”域值
	private String accountNoID;
	// 交易序号
	private String transactionNo;
	// 分期付款期数。“3”、“6”、“9”、“12”、“18”、“24”、“36”
	private String installmentPeriods;
	// 是否分期收取手续费 1---标识手续费分期收取，0---标识手续费不分
	private String isfeeFlag;

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getAccountKeyOne() {
		return accountKeyOne;
	}

	public void setAccountKeyOne(String accountKeyOne) {
		this.accountKeyOne = accountKeyOne;
	}

	public String getAccountKeyTwo() {
		return accountKeyTwo;
	}

	public void setAccountKeyTwo(String accountKeyTwo) {
		this.accountKeyTwo = accountKeyTwo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getBillDateNo() {
		return billDateNo;
	}

	public void setBillDateNo(String billDateNo) {
		this.billDateNo = billDateNo;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountNoID() {
		return accountNoID;
	}

	public void setAccountNoID(String accountNoID) {
		this.accountNoID = accountNoID;
	}

	public String getInstallmentPeriods() {
		return installmentPeriods;
	}

	public void setInstallmentPeriods(String installmentPeriods) {
		this.installmentPeriods = installmentPeriods;
	}

	public String getIsfeeFlag() {
		return isfeeFlag;
	}

	public void setIsfeeFlag(String isfeeFlag) {
		this.isfeeFlag = isfeeFlag;
	}

}
