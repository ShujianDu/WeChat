package com.yada.wechatbank.model;

import java.io.Serializable;

public class BillingSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1661073990314861355L;
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
	// 最小还款额(微信显示：应还金额---20130827修改为closingBalance)
	// 20131107与发卡GCS宋庆楠确认后，最小还款额修改回此字段
	private String minPaymentAmount;
	// 周期最小还款(微信显示：最低还款额)
	// private String closingMinPayment;

	private String cardNo;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BillingSummary [currencyCode=").append(currencyCode);
		sb.append(", periodEndDate=").append(periodEndDate);
		sb.append(", closingBalance=").append(closingBalance);
		sb.append(", periodStartDate=").append(periodStartDate);
		sb.append(", minPaymentAmount=").append(minPaymentAmount);
		sb.append(", paymentDueDate=").append(paymentDueDate);
		// sb.append(", closingMinPayment=").append(closingMinPayment);
		sb.append(", cardNo=").append(cardNo);
		sb.append("]");
		return sb.toString();
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

}
