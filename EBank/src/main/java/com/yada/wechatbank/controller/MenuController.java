package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by QinQiang on 2016/4/21.
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController extends BaseController{

    @Value(value = "${menu.cardApply}")
    private String cardApply;
    @Value(value = "${menu.activity}")
    private String activity;


    @RequestMapping(value = "moreMenu")
    public String moreMenu(Model model) {
        model.addAttribute("cardApply",cardApply);
        return "layout/moreMenu";
    }
}
