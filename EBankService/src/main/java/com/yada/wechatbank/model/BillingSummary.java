package com.yada.wechatbank.model;

/**
 * 账单摘要实体
 * 
 * @author liangtieluan
 *
 */
public class BillingSummary {

	// 账期开始日期(账单日期)
	private String periodStartDate;
	// 账期结束日期/账单日期(微信显示：账单日期)
	private String periodEndDate;
	// 到期还款日 paymentDueDate (微信显示:到期还款日)
	private String paymentDueDate;
	// 本期结欠金额(应还款额：)
	private String closingBalance;
	// 币种(金额类型)
	private String currencyCode;
	// 最小还款额
	private String minPaymentAmount;
	// 卡号
	private String cardNo;
	// 币种中文描述
	private String currencyChinaCode;

	public String getCurrencyChinaCode() {
		return currencyChinaCode;
	}

	public void setCurrencyChinaCode(String currencyChinaCode) {
		this.currencyChinaCode = currencyChinaCode;
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

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMinPaymentAmount() {
		return minPaymentAmount;
	}

	public void setMinPaymentAmount(String minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount;
	}

	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "BillingSummary [periodStartDate=" + periodStartDate + ", periodEndDate=" + periodEndDate + ", paymentDueDate=" + paymentDueDate
				+ ", closingBalance=" + closingBalance + ", currencyCode=" + currencyCode + ", minPaymentAmount=" + minPaymentAmount + ", cardNo=" + cardNo
				+ ", currencyChinaCode=" + currencyChinaCode + "]";
	}

}
