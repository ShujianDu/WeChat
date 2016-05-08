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

    @Value(value = "${menu.bill}")
    private String bill;

    @Value(value = "${menu.balance}")
    private String balance;

    @Value(value = "${menu.points}")
    private String points;

    @Value(value = "${menu.cardApply}")
    private String cardApply;

    @Value(value = "${menu.booking}")
    private String booking;

    @RequestMapping(value = "moreMenu")
    public String moreMenu(Model model) {
        model.addAttribute("bill",bill);
        model.addAttribute("balance",balance);
        model.addAttribute("points",points);
        model.addAttribute("cardApply",cardApply);
        model.addAttribute("booking",booking);
        return "layout/moreMenu";
    }
}
