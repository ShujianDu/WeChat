package com.yada.wechatbank.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

class MyToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    private String username;
    private char[] password;
    private String verification;
    private boolean rememberMe = false;
    private String host;
    private String identityType;
    private String mobileCode;

    MyToken(final String username, final char[] password,
            final String verification, final boolean rememberMe, final String host, String identityType, String mobileCode) {

        this.username = username;
        this.password = password;
        this.verification = verification;
        this.rememberMe = rememberMe;
        this.host = host;
        this.identityType = identityType;
        this.mobileCode = mobileCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityType() {
        return identityType;
    }

    String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}
