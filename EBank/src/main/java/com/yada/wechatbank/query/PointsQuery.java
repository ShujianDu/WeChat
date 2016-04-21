package com.yada.wechatbank.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yada.wechatbank.base.WcbBaseQuery;

/**
 * 积分明细Query
 * 
 * @author zm
 * 
 */
public class PointsQuery {
	private String id;
	private String ParentId;// 父类的ID
	private String TotalPoint;// 有效积分余额
	private String ProductCode;// 产品代码/账号
	private String ProductName;// 产品名称
	private String CardNo;// 信用卡号
	private String Status;// 账户/卡状态描述
	private String PointuseFlg;// 积分账户状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? id : id.trim();
	}

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId == null ? parentId : parentId.trim();
	}

	public String getTotalPoint() {
		return TotalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		TotalPoint = totalPoint == null ? totalPoint : totalPoint.trim();
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode == null ? productCode : productCode.trim();
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName == null ? productName : productName.trim();
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo == null ? cardNo : cardNo.trim();
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status == null ? status : status.trim();
	}

	public String getPointuseFlg() {
		return PointuseFlg;
	}

	public void setPointuseFlg(String pointuseFlg) {
		PointuseFlg = pointuseFlg == null ? pointuseFlg : pointuseFlg.trim();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
