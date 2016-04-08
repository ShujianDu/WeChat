package com.yada.wechatbank.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yada.wechatbank.base.WcbBaseQuery;

/**
 * 申请办卡信息
 * 
 * @author hh
 * 
 */
public class BookingQuery extends WcbBaseQuery {

	/** 客户姓名 */
	private String clientName;
	/** 省ID */
	private String provId;
	/** 市ID */
	private String cityId;
	/** 区ID */
	private String areaId;
	/** 区号（phone=areaCode+phoneNum） */
	private String areaCode;
	/** 联系电话（phone=areaCode+phoneNum） */
	private String phoneNum;
	/** 手机号 */
	private String mobilePhone;
	/** 联系地址 */
	private String serviceAddr;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName == null ? clientName : clientName.trim();
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId == null ? provId : provId.trim();
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId == null ? cityId : cityId.trim();
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId == null ? areaId : areaId.trim();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? areaCode : areaCode.trim();
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum == null ? phoneNum : phoneNum.trim();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone == null ? mobilePhone : mobilePhone
				.trim();
	}

	public String getServiceAddr() {
		return serviceAddr;
	}

	public void setServiceAddr(String serviceAddr) {
		this.serviceAddr = serviceAddr == null ? serviceAddr : serviceAddr
				.trim();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
