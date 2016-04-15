package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.AllCustomerInfoService;
import com.yada.wx.db.service.model.AllCustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 推送消息管理
 * Created by QinQiang on 2016/4/15.
 */
@Controller
@RequestMapping(value = "noticemag")
public class NoticeMagController extends BaseController {

    public static final String LIST_URL = "wechatbank_pages/NoticeMag/list";

    @Autowired
    private AllCustomerInfoService allCustomerInfoService;

    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, Model model) {
        // TODO QQ 添加分享转发代码
        List<AllCustomerInfo> list = allCustomerInfoService.findByIdentityNo(getIdentityNo(request));
        AllCustomerInfo allCustInfo = (list == null || list.size() == 0) ? new AllCustomerInfo() : list.get(0);
        model.addAttribute("dhNotice", allCustInfo.getNotice());
        model.addAttribute("zdNotice", allCustInfo.getBillNotice());
        model.addAttribute("hkNotice", allCustInfo.getRepaymentNotice());
        return LIST_URL;
    }

    @RequestMapping(value = "update")
    public String update(HttpServletRequest request, Model model, String dhNotice, String zdNotice, String hkNotice) {
        boolean result = allCustomerInfoService.updateNoticeByIdentityNo(dhNotice, zdNotice, hkNotice, getIdentityNo(request));
        String msg = result ? "更改成功" : "更改失败";
        model.addAttribute("dhNotice", dhNotice);
        model.addAttribute("zdNotice", zdNotice);
        model.addAttribute("hkNotice", hkNotice);
        model.addAttribute("msg", msg);
        return LIST_URL;
    }

}
