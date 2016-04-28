package com.yada.wechatbank.model;

import java.io.Serializable;

import com.yada.wechatbank.base.BaseModel;

public class BillingDetail extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1348854356388005427L;
	// 卡号
	private String cardNo;
	// 币种
	private String currencyCode;
	// 交易日期
	private String transactionDate;
	// 交易金额
	private String transactionAmount;
	// 交易描述
	private String transactionDescription;
	// 借记,贷记标记
	// "DEBT"表示:借方 (比如：网上消费这种就是借方)
	// "CRED"表示:贷方
	// "NMON"表示:非金融交易
	private String debitCreditCode;
	// 查询开始条数
	private String startnum;
	// 币种中文描述
	private String currencyChinaCode;

	public String getCurrencyChinaCode() {
		return currencyChinaCode;
	}

	public void setCurrencyChinaCode(String currencyChinaCode) {
		this.currencyChinaCode = currencyChinaCode;
	}

	public String getStartnum() {
		return startnum;
	}

	public void setStartnum(String startnum) {
		this.startnum = startnum;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getDebitCreditCode() {
		return debitCreditCode;
	}

	public void setDebitCreditCode(String debitCreditCode) {
		this.debitCreditCode = debitCreditCode;
	}

	@Override
	public String toString() {
		return "BillingDetail [cardNo=" + cardNo + ", currencyCode=" + currencyCode + ", transactionDate=" + transactionDate + ", transactionAmount="
				+ transactionAmount + ", transactionDescription=" + transactionDescription + ", debitCreditCode=" + debitCreditCode + ", startnum=" + startnum
				+ ", currencyChinaCode=" + currencyChinaCode + "]";
	}

}
