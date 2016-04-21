package com.yada.wechatbank.model;

/**
 * 可分期的消费交易实体
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallments {
	// 卡号
	private String cardNo;
	// 交易日期
	private String transactionDate;
	// 交易金额
	private String transactionAmount;
	// 借方、贷方(交易类型)
	private String debitCreditCode;
	// 交易描述
	private String transactionDescription;
	// 账户ID
	private String accountID;
	// 入账账户ID
	private String accountedID;
	// 账户ID -供消费分期费用试算上送用
	private String accountNoID;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getDebitCreditCode() {
		return debitCreditCode;
	}

	public void setDebitCreditCode(String debitCreditCode) {
		this.debitCreditCode = debitCreditCode;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountedID() {
		return accountedID;
	}

	public void setAccountedID(String accountedID) {
		this.accountedID = accountedID;
	}

	public String getAccountNoID() {
		return accountNoID;
	}

	public void setAccountNoID(String accountNoID) {
		this.accountNoID = accountNoID;
	}

	@Override
	public String toString() {
		return "ConsumptionInstallments [cardNo=" + cardNo + ", transactionDate=" + transactionDate + ", transactionAmount=" + transactionAmount
				+ ", debitCreditCode=" + debitCreditCode + ", transactionDescription=" + transactionDescription + ", accountID=" + accountID + ", accountedID="
				+ accountedID + ", accountNoID=" + accountNoID + "]";
	}

}
