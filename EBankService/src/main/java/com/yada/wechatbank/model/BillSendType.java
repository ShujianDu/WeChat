package com.yada.wechatbank.model;

import com.yada.wechatbank.base.BaseModel;

import java.io.Serializable;

/**
 * 账单寄送方式实体
 * @author tx
 */
public class BillSendType  {
	/**
	 * 账单寄送方式
	 * */
	private String cardNo;
	/**
	 * 账单寄送方式---LAN_VALUE
	 * */
	private String billSendType;
	/**
	 * 账单寄送方式---中文描述
	 * */
	private String billSendTypeDesc;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBillSendType() {
		return billSendType;
	}
	public void setBillSendType(String billSendType) {
		this.billSendType = billSendType;
	}
	public String getBillSendTypeDesc() {
		return billSendTypeDesc;
	}
	public void setBillSendTypeDesc(String billSendTypeDesc) {
		this.billSendTypeDesc = billSendTypeDesc;
	}
	
}
