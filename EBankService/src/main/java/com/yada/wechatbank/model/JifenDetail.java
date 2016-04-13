package com.yada.wechatbank.model;

import java.io.Serializable;

public class JifenDetail implements Serializable{

	private static final long serialVersionUID = 6571459829084837943L;
	private String id;
	private String parentId;// 父类的ID
	private String totalPoint;// 有效积分余额
	private String productCode;// 产品代码/账号
	private String productName;// 产品名称
	private String cardNo;// 信用卡号
	private String status;// 账户/卡状态描述
	private String pointuseFlg;// 积分账户状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}

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

	/**
	 *  账户/卡状态描述
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 积分账户状态 : 00正常  01冻结
	 * @return
	 */
	public String getPointuseFlg() {
		return pointuseFlg;
	}

	/**
	 * 积分账户状态 : 00正常  01冻结
	 * @param pointuseFlg
	 */
	public void setPointuseFlg(String pointuseFlg) {
		this.pointuseFlg = pointuseFlg;
	}

	@Override
	public String toString() {
		return "JifengDetail [id=" + id + ", ParentId=" + parentId + ", TotalPoint=" + totalPoint + ", ProductCode="
				+ productCode + ", ProductName=" + productName + ", CardNo=" + cardNo + ", Status=" + status
				+ ", PointuseFlg=" + pointuseFlg + "]";
	}

}
