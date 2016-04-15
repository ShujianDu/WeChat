package com.yada.wechatbank.model;

import java.io.Serializable;

/**
 * @Auther zm
 */
public class SMSCodeManagement implements  Serializable{

	private static final long serialVersionUID = -4771134377704276023L;
	private String id;
	//WeChat用户的唯一标示
	private String openId;
	//短信码
	private String smsCode;
	//创建时间
	private long creatTime;
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
	public String getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
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
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		if (channelCode == null) {
			if (other.channelCode != null)
				return false;
		} else if (!channelCode.equals(other.channelCode))
			return false;
		return true;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SMSCodeManagement [");
		sb.append("Id=").append(id);
		sb.append(", OpenId=").append(openId);
		sb.append(", SmsCode=").append(smsCode);
		sb.append(", CreatTime=").append(creatTime);
		sb.append(", ChannelCode=").append(channelCode);
		sb.append(", count=").append(count);
		sb.append("]");
		return sb.toString();
	}
	
}
