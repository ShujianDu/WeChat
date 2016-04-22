package com.yada.wechatbank.shiro;

import com.yada.wechatbank.permit.PermitHander;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class MyRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private PermitHander permitHander;
    private String timeout;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        // TODO Auto-generated method stub
        String username = (String) principals.fromRealm(getName()).iterator()
                .next();

        return new SimpleAuthorizationInfo();
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {

        MyToken myToken = (MyToken) token;
        // 验证登陆是否正确
        String username = myToken.getUsername();
        String password = new String(myToken.getPassword());
        String verification = myToken.getVerification();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String identityType = myToken.getIdentityType();

        String randomCode = (String) session.getAttribute("jcmsrandomchar");
        String openId = myToken.getOpenId();
        if (openId == null || "".equals(openId)) {
            SavedRequest sr = (SavedRequest) session.getAttribute("shiroSavedRequest");
            openId = getOpenId(sr.getQueryString());
        }

        if (openId != null) {
            if (randomCode.equals(verification)) {
                boolean hasPermit = permitHander.hasPermits(username, password, openId);
                if (hasPermit) {
                    session.setAttribute("identityNo", username);
                    session.setAttribute("identityType", identityType);
                    long time = Long.parseLong(timeout);
                    // 设置session超时时间10分钟
                    SecurityUtils.getSubject().getSession().setTimeout(time);
                    return new SimpleAuthenticationInfo(username, password,
                            getName());
                } else {
                    session.setAttribute("message", "证件号或密码错误！");
                }
            } else {
                session.setAttribute("message", "验证码错误！");
            }
        } else {
            session.setAttribute("message", "非法的URL！");
        }
        return null;
    }

    private String getOpenId(String queryString) {
        String openId = null;
        if (queryString == null) {
            return openId;
        }

        int i = queryString.indexOf("openId=");
        if (i == -1) {
            return openId;
        }

        int j = queryString.indexOf("&", i);
        if (j == -1) {
            j = queryString.length() + 1;
        }

        openId = queryString.substring(i + 7, j);

        return openId;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

}
