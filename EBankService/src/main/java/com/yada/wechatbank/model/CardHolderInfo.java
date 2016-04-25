package com.yada.wechatbank.model;

/**
 * 客户信息
 * @author Tx
 */
public class CardHolderInfo {
	
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
	private String mailBox;
	//单位名称
	private String workUnitName;
	
	// 2015年6月4日因绑定流程由EOM更改为GCS故需要客户信息增加几个字段的获取
	// 客户姓
	private String familyName;
	// 客户名
	private String firstName;
	// 性别
	private String gender;

	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@Override
	public String toString() {
		return "CardHolderInfo [mobileNo=" + mobileNo + ", homeAddressPhone="
				+ homeAddressPhone + ", workUnitPhone=" + workUnitPhone
				+ ", billAddressLine=" + billAddressLine + ", postalCode="
				+ postalCode + ", mailBox=" + mailBox + ", workUnitName="
				+ workUnitName + ",familyName=" + familyName + ",firstName="
				+ firstName + ",gender=" + gender+ "]";
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
	public String getMailBox() {
		return mailBox;
	}
	public void setMailBox(String mailBox) {
		this.mailBox = mailBox;
	}
	public String getWorkUnitName() {
		return workUnitName;
	}
	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}
}
