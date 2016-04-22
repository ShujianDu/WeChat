package com.yada.wechatbank.model;

/**
 * Created by Echo on 2016/4/22.
 */
public class VerificationCardNoResult {
    private String encryptCardNo;

    private String sign;

    public String getEncryptCardNo() {
        return encryptCardNo;
    }

    public void setEncryptCardNo(String encryptCardNo) {
        this.encryptCardNo = encryptCardNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
