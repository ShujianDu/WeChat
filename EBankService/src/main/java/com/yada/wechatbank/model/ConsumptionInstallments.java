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
	// 交易币种
	private String transactionCurrencyCode;
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
	// 清算币种
	private String originalCurrencyCode;
	// 清算金额
	private String originalTransactionAmount;
	// 币种中文描述
	private String currencyChinaCode;
	// 查询开始条数
	private String startnum;
	// 每页显示条数
	private int onepage;
	// 是否有下一页
	private String isFollowUp;
	// 账期号
	private String cycleNumber;
	// 交易序号
	private String transactionNo;

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getCycleNumber() {
		return cycleNumber;
	}

	public void setCycleNumber(String cycleNumber) {
		this.cycleNumber = cycleNumber;
	}

	public String getStartnum() {
		return startnum;
	}

	public String getTransactionCurrencyCode() {
		return transactionCurrencyCode;
	}

	public void setTransactionCurrencyCode(String transactionCurrencyCode) {
		this.transactionCurrencyCode = transactionCurrencyCode;
	}

	public void setStartnum(String startnum) {
		this.startnum = startnum;
	}

	public int getOnepage() {
		return onepage;
	}

	public void setOnepage(int onepage) {
		this.onepage = onepage;
	}

	public String getIsFollowUp() {
		return isFollowUp;
	}

	public void setIsFollowUp(String isFollowUp) {
		this.isFollowUp = isFollowUp;
	}

	public String getCurrencyChinaCode() {
		return currencyChinaCode;
	}

	public void setCurrencyChinaCode(String currencyChinaCode) {
		this.currencyChinaCode = currencyChinaCode;
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

}
