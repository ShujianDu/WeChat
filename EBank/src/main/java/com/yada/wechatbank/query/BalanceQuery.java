package com.yada.wechatbank.query;

/**
 * 额度Query
 * 
 * @author tx
 *
 * 
 */
public class BalanceQuery  {

	/** 卡号 */
	private String cardNo;
	/** 金额类型(币种) */
	private String currencyCode;
	/** 信用额度 */
	private String limitCount;
	/** 总可用额 */
	private String avaliableCount;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo ;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode
				.trim();
	}

	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount == null ? null : limitCount.trim();
	}

	public String getAvaliableCount() {
		return avaliableCount;
	}

	public void setAvaliableCount(String avaliableCount) {
		this.avaliableCount = avaliableCount == null ? null
				: avaliableCount.trim();
	}

}
