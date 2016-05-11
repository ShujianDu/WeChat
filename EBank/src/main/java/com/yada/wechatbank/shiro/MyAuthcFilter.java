package com.yada.wechatbank.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.yada.wechatbank.service.LoginService;
import com.yada.wx.db.service.model.AuthInfo;
import com.yada.wx.db.service.model.CustomerInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class MyAuthcFilter extends FormAuthenticationFilter {

    private static final String DEFAULT_VERIFICATION_PARAM = "verification";
    private String verificationParam = DEFAULT_VERIFICATION_PARAM;     //验证码
    private String identityType = "identityType";
    private String mobileCode = "mobileCode";
    @Autowired
    private LoginService loginService;
    @Value("${session.timeout}")
    private String timeout;

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
        String mobileCode = getMobileCode(request);
        return new MyToken(username, password, verification, rememberMe, host, identityType, mobileCode);
    }

    private String getVerificationParam(ServletRequest request) {
        return WebUtils.getCleanParam(request, getVerificationParam());
    }

    protected String getIdentityType(ServletRequest request) {
        return WebUtils.getCleanParam(request, getIdentityType());
    }

    private String getMobileCode(ServletRequest request) {
        return WebUtils.getCleanParam(request, getMobileCode());
    }

    private String getVerificationParam() {
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

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    private String getMobileCode() {
        return mobileCode;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //从链接中获取code
        String authCode = request.getParameter("authCode");
        if (authCode == null || "".equals(authCode)) {
            return super.isAccessAllowed(request, response, mappedValue);
        }
        //根据code从数据库中查询
        AuthInfo authInfo = loginService.getAuthInfo(authCode);
        if (authInfo != null) {
            //删除数据库中code
            loginService.deleteById(authInfo.getId());
            //根据openId查询用户数据
            CustomerInfo customerInfo = loginService.getCustomerInfo(authInfo.getOpenId());
            if (customerInfo != null) {
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                //将证件号和证件类型放入session
                session.setAttribute("identityNo", customerInfo.getIdentityNo());
                session.setAttribute("identityType", customerInfo.getIdentityType());
                long time = Long.parseLong(timeout);
                // 设置session超时时间
                SecurityUtils.getSubject().getSession().setTimeout(time);
                return true;
            } else {
                return super.isAccessAllowed(request, response, mappedValue);
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
