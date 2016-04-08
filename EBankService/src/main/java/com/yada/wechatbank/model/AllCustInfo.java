package com.yada.wechatbank.model;

import java.io.Serializable;

public class AllCustInfo implements Serializable{
	
	
	private static final long serialVersionUID = -7954872147386679820L;
	
	private String id;// ID
	private String openid;// OPENID
	private String identityType;// 身份证号
	private String identityNo;// 身份证号
	private String bindingDate;// 绑定日期
	private String accId;// 微信账号ID(关注账号)
	private String mobilePhone;// 手机号
	private String sex;// 性别
	private String firstName;// 中文姓
	private String FamilyName;// 中文名
	private String cardNo;// 卡号
	private String cardType;// 卡类型
	private String cardLastFourNumber;//卡号后四位
	private String style;// 产品名称
	private String isDefault;// 是否为默认卡
	private String notice;// 动户通知与否 0-不通知，1-通知
	private String add1;// 备用字段1
	private String add2;// 备用字段2
	private String add3;// 备用字段3
	private String add4;// 备用字段4
	private String add5;// 备用字段5
	private String billNotice;// 账单推送标识
	private String repaymentNotice;// 还款提醒推送标识
	
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getBindingDate() {
		return bindingDate;
	}

	public void setBindingDate(String bindingDate) {
		this.bindingDate = bindingDate;
	}


	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return FamilyName;
	}

	public void setFamilyName(String familyName) {
		FamilyName = familyName;
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

	public String getCardLastFourNumber() {
		return cardLastFourNumber;
	}

	public void setCardLastFourNumber(String cardLastFourNumber) {
		this.cardLastFourNumber = cardLastFourNumber;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getAdd4() {
		return add4;
	}

	public void setAdd4(String add4) {
		this.add4 = add4;
	}

	public String getAdd5() {
		return add5;
	}

	public void setAdd5(String add5) {
		this.add5 = add5;
	}

	
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getBillNotice() {
		return billNotice;
	}

	public void setBillNotice(String billNotice) {
		this.billNotice = billNotice;
	}

	public String getRepaymentNotice() {
		return repaymentNotice;
	}

	public void setRepaymentNotice(String repaymentNotice) {
		this.repaymentNotice = repaymentNotice;
	}

	@Override
	public String toString() {
		return "AllCustInfo [id=" + id + ", openid=" + openid
				+ ", identityType=" + identityType + ", identityNo="
				+ identityNo + ", bindingDate=" + bindingDate + ", accId="
				+ accId + ", mobilePhone=" + mobilePhone + ", sex=" + sex
				+ ", firstName=" + firstName + ", FamilyName=" + FamilyName
				+ ", cardNo=" + cardNo + ", cardType=" + cardType
				+ ", cardLastFourNumber=" + cardLastFourNumber + ", style="
				+ style + ", isDefault=" + isDefault + ", notice=" + notice
				+ ", add1=" + add1 + ", add2=" + add2 + ", add3=" + add3
				+ ", add4=" + add4 + ", add5=" + add5 + ", billNotice="
				+ billNotice + ", repaymentNotice=" + repaymentNotice + "]";
	}

}
