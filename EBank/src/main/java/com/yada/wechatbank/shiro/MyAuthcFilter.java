package com.yada.wechatbank.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyAuthcFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_VERIFICATION_PARAM = "verification";
    private String verificationParam = DEFAULT_VERIFICATION_PARAM;     //验证码
    private String identityType = "identityType";

    /**
     * 重写父类创建token方法
     */
    protected AuthenticationToken createToken(ServletRequest request,
                                              ServletResponse response) {
        String username = getUsername(request);
        char[] password = getPassword(request).toCharArray();
        String verification = getVerificationParam(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String identityType = getIdentityType(request);
        return new MyToken(username, password, verification, rememberMe, host,identityType);
    }

    protected String getVerificationParam(ServletRequest request) {
        return WebUtils.getCleanParam(request, getVerificationParam());
    }

    protected String getIdentityType(ServletRequest request) {
        return WebUtils.getCleanParam(request, getIdentityType());
    }

    public String getVerificationParam() {
        return verificationParam;
    }

    public void setVerificationParam(String verificationParam) {
        this.verificationParam = verificationParam;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityType() {
        return identityType;
    }
}
