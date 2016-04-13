package com.yada.wechatbank.model;

import java.io.Serializable;

/**
 * 按产品查询积分有效期、余额(0013)
 * 
 * @author zm
 * 
 */
public class JifenValidate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -619215266150960161L;
	private String productCode;// 产品代码
	private String productName;// 产品名称
	private String cardNo;// 信用卡卡号
	private String totalPoint;// 积分数
	private String pointExpireDate;// 到期日

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getPointExpireDate() {
		return pointExpireDate;
	}

	public void setPointExpireDate(String pointExpireDate) {
		this.pointExpireDate = pointExpireDate;
	}

	@Override
	public String toString() {
		return "JifengValidate [productCode=" + productCode + ", productName=" + productName + ", cardNo=" + cardNo
				+ ", totalPoint=" + totalPoint + ", pointExpireDate=" + pointExpireDate + "]";
	}

}
