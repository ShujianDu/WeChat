package com.yada.wechatbank.model;

/**
 * 账期实体
 * 
 * @author liangtieluan
 *
 */
public class BillingPeriod {
	// 账户ID
	private String accountId;
	// 币种
	private String currencyCode;
	// 账期开始日期
	private String periodStartDate;
	// 账期结束日期
	private String periodEndDate;
	// 账期号
	private String statementNo;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(String periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public String getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(String periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getStatementNo() {
		return statementNo;
	}

	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}

	@Override
	public String toString() {
		return "BillingPeriod [accountId=" + accountId + ", currencyCode=" + currencyCode + ", periodStartDate=" + periodStartDate + ", periodEndDate="
				+ periodEndDate + ", statementNo=" + statementNo + "]";
	}

}
