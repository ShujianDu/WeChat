package com.yada.wechatbank.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yada.wechatbank.base.WcbBaseQuery;

/**
 * 账单摘要查询实体
 * 
 * @author liangtieluan
 *
 */
public class BillingSummaryQuery extends WcbBaseQuery {
	/** 账期开始日期(账单日期) */
	private String periodStartDate;
	/** 账期结束日期/账单日期(到期还款日) */
	private String periodEndDate;
	/** 本期结欠金额(应还款额：) */
	private String closingBalance;
	/** 币种(金额类型) */
	private String currencyCode;
	/** 最小还款额(最低还款额：) */
	private String minPaymentAmount;
	/** 查询卡号 */
	private String cardNo;
	/** 账单类型（0已出或1未出） */
	private String billType;
	/** 账单日期 */
	private String date;
	/** 账单类型 ALLT已出账单 UNSM未出账单 */
	private String queryType;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date == null ? date : date.trim();
	}

	public String getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(String periodStartDate) {
		this.periodStartDate = periodStartDate == null ? periodStartDate : periodStartDate.trim();
	}

	public String getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(String periodEndDate) {
		this.periodEndDate = periodEndDate == null ? periodEndDate : periodEndDate.trim();
	}

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance == null ? closingBalance : closingBalance.trim();
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? currencyCode : currencyCode.trim();
	}

	public String getMinPaymentAmount() {
		return minPaymentAmount;
	}

	public void setMinPaymentAmount(String minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount == null ? minPaymentAmount : minPaymentAmount.trim();
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType == null ? billType : billType.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType == null ? queryType : queryType.trim();
	}
}
