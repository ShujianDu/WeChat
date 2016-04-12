package com.yada.wechatbank.model;


public class CardHolderInfo{
	
	//手机号码
	private String mobileNo;
	//住宅电话
	//住宅电话与2013年11月21日经确认从productSign更新为homeAddressPhone 起因请参看事件99199
	//private String productSign;
	private String homeAddressPhone;
	//单位电话
	private String workUnitPhone;
	//账单地址
	private String billAddressLine;
	//邮政编码
	private String postalCode;
	//电子邮箱
	private String eMail;
	//单位名称
	private String workUnitName;
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getWorkUnitPhone() {
		return workUnitPhone;
	}
	public String getHomeAddressPhone() {
		return homeAddressPhone;
	}
	public void setHomeAddressPhone(String homeAddressPhone) {
		this.homeAddressPhone = homeAddressPhone;
	}
	public void setWorkUnitPhone(String workUnitPhone) {
		this.workUnitPhone = workUnitPhone;
	}
	public String getBillAddressLine() {
		return billAddressLine;
	}
	public void setBillAddressLine(String billAddressLine) {
		this.billAddressLine = billAddressLine;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEMail() {
		return eMail;
	}
	public void setEMail(String EMail) {
		this.eMail = EMail;
	}
	public String getWorkUnitName() {
		return workUnitName;
	}
	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}
}
