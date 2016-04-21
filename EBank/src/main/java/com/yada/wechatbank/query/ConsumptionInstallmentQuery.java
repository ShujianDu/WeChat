package com.yada.wechatbank.query;

import java.io.Serializable;

/**
 * 消费分期查询(TS011007) 返回实体
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentQuery implements Serializable {

	private static final long serialVersionUID = -648906121202330339L;

	// 帐户ID
	private String accountID;
	// 周期号
	private String cycleNumber;
	// 交易序号
	private String transactionNo;
	// 帐户ID（入帐）
	private String accountedID;
	// 清算币种
	private String originalCurrencyCode;
	// 清算金额
	private String originalTransactionAmount;
	// 卡号
	private String cardNo;
	// 交易日期
	private String transactionDate;
	// 记帐日期
	private String billDate;
	// 交易币种
	private String transactionCurrencyCode;
	// 记帐币种
	private String billCurrencyCode;
	// 交易金额
	private String transactionAmount;
	// 交易金额(GCS返回报文中交易金额原值)
	private String transactionAmountMessage;
	// 记帐金额
	private String billAmount;
	// 借方、贷方(交易类型)
	private String debitCreditCode;
	// 交易代码
	private String transactionCode;
	// 交易状态代码
	private String transactionStateCode;
	// 争议日期
	private String dateTxnDisputed;
	// 释放日期
	private String dateReleasedFromDispute;
	// 交易描述
	private String transactionDescription;
	// 账号id1
	private String accountNoID;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getCycleNumber() {
		return cycleNumber;
	}

	public void setCycleNumber(String cycleNumber) {
		this.cycleNumber = cycleNumber;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getAccountedID() {
		return accountedID;
	}

	public void setAccountedID(String accountedID) {
		this.accountedID = accountedID;
	}

	public String getOriginalCurrencyCode() {
		return originalCurrencyCode;
	}

	public void setOriginalCurrencyCode(String originalCurrencyCode) {
		this.originalCurrencyCode = originalCurrencyCode;
	}

	public String getOriginalTransactionAmount() {
		return originalTransactionAmount;
	}

	public void setOriginalTransactionAmount(String originalTransactionAmount) {
		this.originalTransactionAmount = originalTransactionAmount;
	}

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

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getTransactionCurrencyCode() {
		return transactionCurrencyCode;
	}

	public void setTransactionCurrencyCode(String transactionCurrencyCode) {
		this.transactionCurrencyCode = transactionCurrencyCode;
	}

	public String getBillCurrencyCode() {
		return billCurrencyCode;
	}

	public void setBillCurrencyCode(String billCurrencyCode) {
		this.billCurrencyCode = billCurrencyCode;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionAmountMessage() {
		return transactionAmountMessage;
	}

	public void setTransactionAmountMessage(String transactionAmountMessage) {
		this.transactionAmountMessage = transactionAmountMessage;
	}

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	public String getDebitCreditCode() {
		return debitCreditCode;
	}

	public void setDebitCreditCode(String debitCreditCode) {
		this.debitCreditCode = debitCreditCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionStateCode() {
		return transactionStateCode;
	}

	public void setTransactionStateCode(String transactionStateCode) {
		this.transactionStateCode = transactionStateCode;
	}

	public String getDateTxnDisputed() {
		return dateTxnDisputed;
	}

	public void setDateTxnDisputed(String dateTxnDisputed) {
		this.dateTxnDisputed = dateTxnDisputed;
	}

	public String getDateReleasedFromDispute() {
		return dateReleasedFromDispute;
	}

	public void setDateReleasedFromDispute(String dateReleasedFromDispute) {
		this.dateReleasedFromDispute = dateReleasedFromDispute;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getAccountNoID() {
		return accountNoID;
	}

	public void setAccountNoID(String accountNoID) {
		this.accountNoID = accountNoID;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ConsumptionInstallmentQuery [accountID=").append(accountID);
		sb.append(", cycleNumber=").append(cycleNumber);
		sb.append(", transactionNo=").append(transactionNo);
		sb.append(", accountedID=").append(accountedID);
		sb.append(", originalCurrencyCode=").append(originalCurrencyCode);
		sb.append(", originalTransactionAmount=").append(originalTransactionAmount);
		sb.append(", cardNo=").append(cardNo);
		sb.append(", transactionDate=").append(transactionDate);
		sb.append(", billDate=").append(billDate);
		sb.append(", transactionCurrencyCode=").append(transactionCurrencyCode);
		sb.append(", billCurrencyCode=").append(billCurrencyCode);
		sb.append(", transactionAmount=").append(transactionAmount);
		sb.append(", transactionAmountMessage=").append(transactionAmountMessage);
		sb.append(", billAmount=").append(billAmount);
		sb.append(", debitCreditCode=").append(debitCreditCode);
		sb.append(", transactionCode=").append(transactionCode);
		sb.append(", transactionStateCode=").append(transactionStateCode);
		sb.append(", dateTxnDisputed=").append(dateTxnDisputed);
		sb.append(", dateReleasedFromDispute=").append(dateReleasedFromDispute);
		sb.append(", transactionDescription=").append(transactionDescription);
		sb.append(", accountNoID=").append(accountNoID);
		sb.append("]");
		return sb.toString();
	}

}
