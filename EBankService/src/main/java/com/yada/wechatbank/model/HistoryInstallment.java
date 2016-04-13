package com.yada.wechatbank.model;

import java.io.Serializable;

/**
 * 分期付款查询(TS011021)返回实体
 * @author zm
 *
 */
public class HistoryInstallment implements Serializable{

	private static final long serialVersionUID = -5925837661240179159L;
	
	//卡号
	private String cardNo;
	//分期付款交易日期(页面：分期日期)
	private String instalmentOriginalTransactionDate;
	//分期付款计划描述(页面：分期描述)
	private String instalmentRuleDescription;
	//货币代码(页面：分期币种)
	private String currencyCode;
	//分期付款原始金额(页面：分期金额)
	private String instalmentOriginalAmount;
	//分期付款期数(页面：期数)
	private String instalmentOriginalNumber;
	//分期付款完成日期(页面：完成日期)
	private String instalmentCompleteDate;
	//分期手续费收取方式
	private String instFeeFlag;
	//首次入帐金额
	private String instalmentFirstPostingAmount;
	//下次入帐金额
	private String instalmentNextPostingAmount;
	//分期付款已入帐期数(页面：已入账期数)
	private String instalmentPostedNumber;
	//分期付款冲正金额(页面：已入账金额)
	private String instalmentReversalAmount;
	//分期付款剩余期数(页面：剩余未入账期数)
	private String instalmentOutstandingNumber;
	//分期付款剩余金额(页面：剩余未入账金额)
	private String instalmentOutstandingAmount;
	//下次入帐日期
	private String instalmentNextPostingDate;
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getInstalmentOriginalTransactionDate() {
		return instalmentOriginalTransactionDate;
	}

	public void setInstalmentOriginalTransactionDate(
			String instalmentOriginalTransactionDate) {
		this.instalmentOriginalTransactionDate = instalmentOriginalTransactionDate;
	}

	public String getInstalmentRuleDescription() {
		return instalmentRuleDescription;
	}

	public void setInstalmentRuleDescription(String instalmentRuleDescription) {
		this.instalmentRuleDescription = instalmentRuleDescription;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getInstalmentOriginalAmount() {
		return instalmentOriginalAmount;
	}

	public void setInstalmentOriginalAmount(String instalmentOriginalAmount) {
		this.instalmentOriginalAmount = instalmentOriginalAmount;
	}

	public String getInstalmentOriginalNumber() {
		return instalmentOriginalNumber;
	}

	public void setInstalmentOriginalNumber(String instalmentOriginalNumber) {
		this.instalmentOriginalNumber = instalmentOriginalNumber;
	}

	public String getInstalmentCompleteDate() {
		return instalmentCompleteDate;
	}

	public void setInstalmentCompleteDate(String instalmentCompleteDate) {
		this.instalmentCompleteDate = instalmentCompleteDate;
	}

	public String getInstFeeFlag() {
		return instFeeFlag;
	}

	public void setInstFeeFlag(String instFeeFlag) {
		this.instFeeFlag = instFeeFlag;
	}

	public String getInstalmentFirstPostingAmount() {
		return instalmentFirstPostingAmount;
	}

	public void setInstalmentFirstPostingAmount(String instalmentFirstPostingAmount) {
		this.instalmentFirstPostingAmount = instalmentFirstPostingAmount;
	}

	public String getInstalmentNextPostingAmount() {
		return instalmentNextPostingAmount;
	}

	public void setInstalmentNextPostingAmount(String instalmentNextPostingAmount) {
		this.instalmentNextPostingAmount = instalmentNextPostingAmount;
	}

	public String getInstalmentPostedNumber() {
		return instalmentPostedNumber;
	}

	public void setInstalmentPostedNumber(String instalmentPostedNumber) {
		this.instalmentPostedNumber = instalmentPostedNumber;
	}

	public String getInstalmentReversalAmount() {
		return instalmentReversalAmount;
	}

	public void setInstalmentReversalAmount(String instalmentReversalAmount) {
		this.instalmentReversalAmount = instalmentReversalAmount;
	}

	public String getInstalmentOutstandingNumber() {
		return instalmentOutstandingNumber;
	}

	public void setInstalmentOutstandingNumber(String instalmentOutstandingNumber) {
		this.instalmentOutstandingNumber = instalmentOutstandingNumber;
	}

	public String getInstalmentOutstandingAmount() {
		return instalmentOutstandingAmount;
	}

	public void setInstalmentOutstandingAmount(String instalmentOutstandingAmount) {
		this.instalmentOutstandingAmount = instalmentOutstandingAmount;
	}

	public String getInstalmentNextPostingDate() {
		return instalmentNextPostingDate;
	}

	public void setInstalmentNextPostingDate(String instalmentNextPostingDate) {
		this.instalmentNextPostingDate = instalmentNextPostingDate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ConsumptionInstallmentCost [cardNo=").append(cardNo);
		sb.append(", instalmentOriginalTransactionDate=").append(instalmentOriginalTransactionDate);
		sb.append(", instalmentRuleDescription=").append(instalmentRuleDescription);
		sb.append(", currencyCode=").append(currencyCode);
		sb.append(", instalmentOriginalAmount=").append(instalmentOriginalAmount);
		sb.append(", instalmentOriginalNumber=").append(instalmentOriginalNumber);
		sb.append(", instalmentCompleteDate=").append(instalmentCompleteDate);
		sb.append(", instFeeFlag=").append(instFeeFlag);
		sb.append(", instalmentFirstPostingAmount=").append(instalmentFirstPostingAmount);
		sb.append(", instalmentNextPostingAmount=").append(instalmentNextPostingAmount);
		sb.append(", instalmentPostedNumber=").append(instalmentPostedNumber);
		sb.append(", instalmentReversalAmount=").append(instalmentReversalAmount);
		sb.append(", instalmentOutstandingNumber=").append(instalmentOutstandingNumber);
		sb.append(", instalmentOutstandingAmount=").append(instalmentOutstandingAmount);
		sb.append(", instalmentNextPostingDate=").append(instalmentNextPostingDate);
		sb.append("]");
		return sb.toString();
	}

}
