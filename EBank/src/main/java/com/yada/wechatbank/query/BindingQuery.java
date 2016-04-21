package com.yada.wechatbank.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yada.wechatbank.base.WcbBaseQuery;

/**
 * 
 * @author zm
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
	/**手机号*/
	private String mobilNo;

	public String getMobilNo() {
		return mobilNo;
	}

	public void setMobilNo(String mobilNo) {
		this.mobilNo = mobilNo;
	}

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
}
