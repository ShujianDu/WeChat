package com.yada.wechatbank.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;

@Controller
@RequestMapping(value = "login")
public class LoginController extends BaseController {

    private static final String LOGINURL = "wechatbank_pages/Login/login";
    private static final String WELCOMEURL = "wechatbank_pages/Login/index";

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
        //认证失败，将用户名回显
        String username = request.getParameter("username");
        String identityType = request.getParameter("idType");
        if (!"".equals(username)) {
            model.addAttribute("username", username);
        }
        if (!"".equals(identityType)) {
            model.addAttribute("identityType", identityType);
        }
        return LOGINURL;
    }
}
