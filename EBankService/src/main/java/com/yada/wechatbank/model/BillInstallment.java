package com.yada.wechatbank.model;

/**
 * 账单授权实体
 * @author Tx
 */

public class BillInstallment  {

	//卡号
	private String id;
	//卡号
	private String cardNo;
	//交易描述
	private String tradingDec;
	//币种
	private String currencyCode;
	//分期金额
	private String instalAmount;
	//分期期数
	private String instalCount;
	//分期手续费收取方式
	private String feeMethod;
	//交易日期
	private String tradingDate;
	//交易状态
	private String status;
	//GCS报文头返回码（+GC00000=Success）
	private String gcsCode;
	//备注
	private String remark;

	public BillInstallment(String cardNo, String tradingDec, String currencyCode,
					   String instalAmount, String instalCount, String feeMethod,String tradingDate) {
		this.cardNo = cardNo;
		this.tradingDec = tradingDec;
		this.currencyCode = currencyCode;
		this.instalAmount = instalAmount;
		this.instalCount = instalCount;
		this.feeMethod = feeMethod;
		this.tradingDate = tradingDate;
	}
	public BillInstallment(){}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTradingDec() {
		return tradingDec;
	}
	public void setTradingDec(String tradingDec) {
		this.tradingDec = tradingDec;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getInstalAmount() {
		return instalAmount;
	}
	public void setInstalAmount(String instalAmount) {
		this.instalAmount = instalAmount;
	}
	public String getInstalCount() {
		return instalCount;
	}
	public void setInstalCount(String instalCount) {
		this.instalCount = instalCount;
	}
	public String getFeeMethod() {
		return feeMethod;
	}
	public void setFeeMethod(String feeMethod) {
		this.feeMethod = feeMethod;
	}
	public String getTradingDate() {
		return tradingDate;
	}
	public void setTradingDate(String tradingDate) {
		this.tradingDate = tradingDate;
	}
	public String getGcsCode() {
		return gcsCode;
	}
	public void setGcsCode(String gcsCode) {
		this.gcsCode = gcsCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
	