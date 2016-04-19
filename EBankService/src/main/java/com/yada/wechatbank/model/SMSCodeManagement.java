package com.yada.wechatbank.model;

/**
 * 短信验证码实体
 * @auther tx
 */
public class SMSCodeManagement {
	//证件号
	private String identityNo;
	//手机号
	private String mobile;
	//短信码
	private String smsCode;
	//渠道编号
	private String channelCode;
	//尝试次数
	private int count=0;

	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}



	public SMSCodeManagement(){}

	public SMSCodeManagement(String identityNo, String mobile, String channelCode,String smsCode) {
		this.identityNo = identityNo;
		this.mobile = mobile;
		this.smsCode = smsCode;
		this.channelCode = channelCode;
	}

	@Override
	public String toString() {
		return "SMSCodeManagement [" + " identityNo=" + identityNo + ", mobile=" + mobile + ", SmsCode=" + smsCode + ", ChannelCode=" + channelCode + ", count=" + count + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identityNo == null) ? 0 : identityNo.hashCode());
		result = prime * result + ((channelCode == null) ? 0 : channelCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SMSCodeManagement other = (SMSCodeManagement) obj;
		if (identityNo == null) {
			if (other.identityNo != null)
				return false;
		} else if (!identityNo.equals(other.identityNo))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (channelCode == null) {
			if (other.channelCode != null)
				return false;
		} else if (!channelCode.equals(other.channelCode))
			return false;
		return true;
	}
}
