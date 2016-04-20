package com.yada.wechatbank.model;


import com.yada.wechatbank.base.BaseModel;

/**
 * 额度实体
 * @author Tx
 */
public class Balance  {
	// 卡号
	private String cardNo;
	// 金额类型(币种)
	private String currencyCode;
	// 金额中文描述(币种)
	private String currencyChinaCode;
	// 总授信额度
	private String limitCount;
	// 总可用额
	private String avaliableCount;
	// 取现可用额度
	private String preCashAdvanceCreditLimit;

	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}

	public String getAvaliableCount() {
		return avaliableCount;
	}

	public void setAvaliableCount(String avaliableCount) {
		this.avaliableCount = avaliableCount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyChinaCode() {
		return currencyChinaCode;
	}

	public void setCurrencyChinaCode(String currencyChinaCode) {
		this.currencyChinaCode = currencyChinaCode;
	}
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPreCashAdvanceCreditLimit() {
		return preCashAdvanceCreditLimit;
	}

	public void setPreCashAdvanceCreditLimit(String preCashAdvanceCreditLimit) {
		this.preCashAdvanceCreditLimit = preCashAdvanceCreditLimit;
	}
}
