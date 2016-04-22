package com.yada.wechatbank.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 菜单回显Handler
 * Created by QinQiang on 2016/4/21.
 */
public class MenuHandler extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getParameter("menuId") != null && !"".equals(request.getParameter("menuId"))) {
            request.getSession().setAttribute("menuId", request.getParameter("menuId"));
        }
        return super.preHandle(request, response, handler);
    }
}
