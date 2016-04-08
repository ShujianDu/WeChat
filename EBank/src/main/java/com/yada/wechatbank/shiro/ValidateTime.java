package com.yada.wechatbank.shiro;

import java.util.Date;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.DateTimeUtil;


public class ValidateTime {
	private String timeout;
	
	
	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public static String[] getStr(String code){
		String openIdAndTime = null;
		try {
			openIdAndTime = Crypt.decode(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] ss = openIdAndTime.split(";");
		return ss;
	}
	
	public  Boolean hasPermit(String openId,String code){
		String[] ss = getStr(code);
		if(openId == null | ss[0]==null){
			return false;
		}
		if(!openId.equals(ss[0])){//判断openId是否相同
			return false;
		}
		DateTimeUtil dateTimeUtil = new DateTimeUtil();
		Date datetime = null;
		datetime = dateTimeUtil.parseDateTime(ss[1]);
		Date date = dateTimeUtil.getPreTime(Integer.parseInt(timeout), 1);
		return date.before(datetime);
	}
	
}
