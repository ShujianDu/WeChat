package com.yada.wechatbank.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yada.wechatbank.base.WcbBaseQuery;
import com.yada.wechatbank.util.Crypt;

/**
 * 
 * @author hh
 * 
 */
public class BindingQuery extends WcbBaseQuery {
	/** 身份证号码 */
	private String idNumber;
	/** 证件类型*/
	private String idType;
	/** 查询密码 */
	private String passwordQuery;
	/** 验证码 */
	private String verificationCode;
	/** 默认绑定卡号 */
	private String defaultCard;
	/**	公众平台Id toUser */
	private String toUser;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber == null ? idNumber : idNumber.trim();
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getPasswordQuery() {
		return passwordQuery;
	}

	public void setPasswordQuery(String passwordQuery) {
		this.passwordQuery = passwordQuery == null ? passwordQuery
				: passwordQuery.trim();
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode == null ? verificationCode
				: verificationCode.trim();
	}

	public String getDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(String defaultCard) {
		this.defaultCard = defaultCard == null ? defaultCard
				: defaultCard.trim();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
}
