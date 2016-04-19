package com.yada.wechatbank.model;

import com.yada.wechatbank.base.BaseModel;

import java.io.Serializable;

/**
 * 账单金额上下限实体
 * @author tx
 *
 */
public class AmountLimit extends BaseModel{

	/* 账户ID */
	public String accountId;
	//账户号码
	public String accountNo;
	//币种
	public String currencyCode;
	//账单分期金额下限真实
	public String minAmount;
	//账单分期金额下限显示
	public String showMinAmount;
	//账单分期金额上限
	public String maxAmount;
	//返回码
	public String respCode;

	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getShowMinAmount() {
		return showMinAmount;
	}

	public void setShowMinAmount(String showMinAmount) {
		this.showMinAmount = showMinAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String showMinAmount) {
		this.accountNo = accountNo;
	}
}
