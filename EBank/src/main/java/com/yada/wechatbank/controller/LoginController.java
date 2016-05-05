package com.yada.wechatbank.controller;

import javax.servlet.http.HttpServletRequest;

import com.yada.wechatbank.service.LoginService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.IdTypeUtil;
import com.yada.wechatbank.util.TokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "login")
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String LOGINURL = "wechatbank_pages/Login/login";
    private static final String WELCOMEURL = "wechatbank_pages/Login/index";
    @Autowired
    private SmsService smsServiceImpl;
    @Autowired
    private LoginService loginService;


    /**
     * 信用卡微信客服登录
     *
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();
        // 如果已经通过认证，直接访问该login方法，返回欢迎页面
        if (subject.isAuthenticated()) {
            return WELCOMEURL;
        }
        //认证失败，向页面返回错误信息
        Session session = subject.getSession();
        String msg = (String) session.getAttribute("message");
        if (msg != null) {
            model.addAttribute("message", msg);
            session.removeAttribute("message");
        }
        String username = request.getParameter("username");
        String identityType = request.getParameter("idType");
        //认证失败，将用户名回显
        if (!"".equals(username)) {
            model.addAttribute("username", username);
        }
        if (!"".equals(identityType)) {
            model.addAttribute("identityType", identityType);
        }
        return LOGINURL;
    }
    /**
     * 获取短信验证码
     *
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "getSMSCode_ajax")
    @ResponseBody
    public String getSMSCode_ajax(HttpServletRequest request) throws IOException {
        String result = "false";
        String identityType = request.getParameter("idType");
        String identityNo = request.getParameter("identityNo");
        // Session中存储的验证码
        String Randomcode_yz = (String) request.getSession().getAttribute(
                "jcmsrandomchar");
        String verificationCode = request.getParameter("verificationCode");
        if (verificationCode != null
                && verificationCode.equalsIgnoreCase(Randomcode_yz)) {
            if (!"".equals(identityNo) && !"".equals(identityType)) {
                //后台查询手机号
                String mobile = loginService.getMobileNo(identityType,identityNo);
                if ("exception".equals(mobile) || "noMobileNumber".equals(mobile)) {
                    result = mobile;
                } else {
                    //调用发送短信验证码方法
                    boolean sendResult = smsServiceImpl.sendLoginSMS(identityNo, mobile, "login");
                    if (!sendResult) {
                        result = "exception";
                    } else {
                        //验证码发送成功记录证件号，防止黑客修改卡号后再验证查询密码
                        request.getSession().setAttribute("idNum", identityNo);
                        TokenUtil.addToken(request);
                        result = sendResult + "," + request.getSession().getAttribute("keyInSession");
                    }
                }
            }
        } else {
            result = "errorCode";
        }
        return result;
    }
}
