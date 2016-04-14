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
	// 交易类型（支出，还款） XXX暂时不用
	// private String transactionType;
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

	public String getStartnum() {
		return startnum;
	}

	public void setStartnum(String startnum) {
		this.startnum = startnum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BillingDetail [cardNo=").append(cardNo);
		sb.append(", currencyCode=").append(currencyCode);
		sb.append(", transactionDate=").append(transactionDate);
		sb.append(", transactionAmount=").append(transactionAmount);
		sb.append(", debitCreditCode=").append(debitCreditCode);
		sb.append(", transactionDescription=").append(transactionDescription);
		sb.append("]");
		return sb.toString();
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

}
