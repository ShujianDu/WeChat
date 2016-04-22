package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by QinQiang on 2016/4/21.
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController extends BaseController{

    @RequestMapping(value = "moreMenu")
    public String moreMenu() {
        return "layout/moreMenu";
    }
}
