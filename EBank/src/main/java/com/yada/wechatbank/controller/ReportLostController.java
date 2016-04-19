package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.ReportLostService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡挂失
 * Created by QinQiang on 2016/4/18.
 */
@Controller
@RequestMapping(value = "reportlost")
public class ReportLostController extends BaseController {

    private static final String LIST_URL = "wechatbank_pages/ReportLost/list"; // 挂失
    private static final String CANCEL_URL = "wechatbank_pages/ReportLost/cancel"; // 解除临时挂失
    private static final String RESULT_URL = "wechatbank_pages/ReportLost/result"; // 结果页面

    private static final String entyMethod = "01"; // 录入方式
    private static final String lossReason = "02";// 挂失原因

    @Autowired
    private ReportLostService reportLostService;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request) {
        List<String> cardNoList = reportLostService.selectCardNoList(getIdentityType(request), getIdentityNo(request));
        // TODO QQ Test代码，提交删除
        if (cardNoList == null) {
            cardNoList = new ArrayList<>();
            cardNoList.add("6225990148528293");
        }
        if (cardNoList == null) {
            return BUSYURL;
        } else if (cardNoList.size() == 0) {
            return NOCARDURL;
        }
        try {
            Crypt.cardNoCrypt(cardNoList);
        } catch (Exception e) {
            e.printStackTrace();
            return BUSYURL;
        }
        model.addAttribute("cardList", cardNoList);
        return LIST_URL;
    }


    @RequestMapping(value = "reprotlost")
    public String reprotLost(String cardNo, String reportType, Model model, HttpServletRequest request) {
        String msg;
        boolean result;
        if ("1".equals(reportType)) { // 临时挂失
            result = reportLostService.tempCreditCardReportLost(cardNo, entyMethod, getIdentityType(request), getIdentityNo(request), lossReason);
            msg = result ? "临时挂失成功" : "临时挂失失败";
        } else if ("2".equals(reportType)) { // 永久挂失
            result = reportLostService.creditCardReportLost(cardNo, getIdentityType(request), getIdentityNo(request), lossReason);
            msg = result ? "永久挂失成功" : "永久挂失失败";
        } else {
            return ERROR;
        }
        model.addAttribute("msg", msg);
        model.addAttribute("result", result);
        return RESULT_URL;
    }

    @RequestMapping(value = "cancel")
    public String cancel(Model model, HttpServletRequest request) {
        List<String> cardNoList = reportLostService.selectCardNoList(getIdentityType(request), getIdentityNo(request));
        // TODO QQ Test代码，提交删除
        if (cardNoList == null) {
            cardNoList = new ArrayList<>();
            cardNoList.add("6225990148528293");
        }
        if (cardNoList == null) {
            return BUSYURL;
        } else if (cardNoList.size() == 0) {
            return NOCARDURL;
        }
        try {
            Crypt.cardNoCrypt(cardNoList);
        } catch (Exception e) {
            e.printStackTrace();
            return BUSYURL;
        }
        model.addAttribute("cardList", cardNoList);
        return CANCEL_URL;
    }

    @RequestMapping(value = "doCancel")
    public String doCancel(String cardNo, Model model, HttpServletRequest request) {
        String msg;
        boolean result;
        try {
            cardNo = Crypt.decode(cardNo);
            result = reportLostService.relieveCreditCardTempReportLost(cardNo, getIdentityType(request), getIdentityNo(request));
            msg = result ? "解除临时挂失成功" : "解除临时挂失失败";
        } catch (Exception e) {
            e.printStackTrace();
            return BUSYURL;
        }
        model.addAttribute("msg", msg);
        model.addAttribute("result", result);
        return RESULT_URL;
    }

    @ResponseBody
    @RequestMapping(value = "getMsgCode_ajax")
    public String getMsgCode_ajax() {
        String result = "true";
        // TODO QQ 调用发送短信模块
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "checkMsgCode_ajax")
    public String checkMsgCode_ajax() {
        // TODO QQ 完善验证短信验证码
        String result = "true";
        return result;
    }

}
