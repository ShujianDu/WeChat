package com.yada.wechatbank.model;

import java.io.Serializable;

public class CardInfo implements Serializable{
	
	private static final long serialVersionUID = 5162913640087288966L;
	
	private String id;// ID
	private String cardNo;// 卡号
	private String cardType;// 卡类
	private String updateDate;// 更新时间
	private String infoId;// 关联客户表ID
	private String openid;// OPENID
	private String customerId;// 客户号
	private String isDefault;// 是否是默认卡(0 是，1 否)
	private String currency;// 币种
	private String style;// 产品名称
	private String mainFlag;// 主副卡标识

	public String getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public boolean equals(Object arg0) {
		CardInfo cardInfo = (CardInfo)arg0;
		return cardInfo.cardNo.equals(cardNo);
	}

	@Override
	public int hashCode() {
		return cardNo.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardInfo [ id = "+id);
		sb.append(", cardNo="+cardNo);
		sb.append(", cardType="+cardType);
		sb.append(", updateDate="+updateDate);
		sb.append(", infoId="+infoId);
		sb.append(", openid="+openid);
		sb.append(", customerId="+customerId);
		sb.append(", isDefault="+isDefault);
		sb.append(", currency="+currency);
		sb.append(", style="+style);
		sb.append(", mainFlag="+mainFlag);
		sb.append(" ]");
		return sb.toString();
	}
}
