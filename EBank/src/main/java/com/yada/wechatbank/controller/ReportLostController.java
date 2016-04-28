package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.ReportLostService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 信用卡挂失
 * Created by QinQiang on 2016/4/18.
 */
@Controller
@RequestMapping(value = "reportlost")
public class ReportLostController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LIST_URL = "wechatbank_pages/ReportLost/list"; // 挂失
    private static final String CANCEL_URL = "wechatbank_pages/ReportLost/cancel"; // 解除临时挂失
    private static final String RESULT_URL = "wechatbank_pages/ReportLost/result"; // 结果页面

    private static final String entyMethod = "01"; // 录入方式
    private static final String lossReason = "02";// 挂失原因

    @Autowired
    private ReportLostService reportLostService;

    /**
     * 进入挂失页面
     *
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request) {
        String identityType = getGcsIdentityType(request);
        String identityNo = getIdentityNo(request);

        logger.info("@ReportLost@挂失查询卡列表，参数：[identityType={},identityNo={}]", identityType, identityNo);
        List<String> cardNoList = reportLostService.selectCardNoList(identityType, identityNo);
        logger.info("@ReportLost@挂失查询卡列表，参数：[identityType={},identityNo={}]，结果：[{}]", identityType, identityNo, cardNoList);
        if (cardNoList == null) {
            return BUSYURL;
        } else if (cardNoList.size() == 0) {
            return NOCARDURL;
        }
        try {
            Crypt.cardNoCrypt(cardNoList);
        } catch (Exception e) {
            logger.error("@ReportLost@加密卡列表出现异常");
            return BUSYURL;
        }
        model.addAttribute("cardList", cardNoList);
        return LIST_URL;
    }

    /**
     * 挂失
     *
     * @param cardNo     卡号
     * @param reportType 挂失类型：1-临时挂失，2-永久挂失
     * @param model      Model
     * @param request    HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "reprotlost")
    public String reprotLost(String cardNo, String reportType, Model model, HttpServletRequest request) {
        String msg;
        boolean result;
        String identityType = getGcsIdentityType(request);
        String identityNo = getIdentityNo(request);
        try {
            cardNo = Crypt.decode(cardNo);
        } catch (Exception e) {
            logger.error("@ReportLost@解密卡号出现异常,[cardNo={}]", cardNo, e);
            return BUSYURL;
        }
        if ("1".equals(reportType)) { // 临时挂失
            logger.info("@ReportLost@执行临时挂失，参数：[cardNo={},entyMethod={},identityType={},identityNo={},lossReason={}]", cardNo, entyMethod, identityType, identityNo, lossReason);
            result = reportLostService.tempCreditCardReportLost(cardNo, entyMethod, identityType, identityNo, lossReason);
            logger.info("@ReportLost@执行临时挂失，参数：[cardNo={},entyMethod={},identityType={},identityNo={},lossReason={}]，结果：[{}]", cardNo, entyMethod, identityType, identityNo, lossReason, result);
            msg = result ? "临时挂失成功" : "临时挂失失败";
        } else if ("2".equals(reportType)) { // 永久挂失
            logger.info("@ReportLost@执行临时挂失，参数：[cardNo={},identityType={},identityNo={},lossReason={}]", cardNo, identityType, identityNo, lossReason);
            result = reportLostService.creditCardReportLost(cardNo, identityType, identityNo, lossReason);
            logger.info("@ReportLost@执行临时挂失，参数：[cardNo={},identityType={},identityNo={},lossReason={}]，结果：[{}]", cardNo, identityType, identityNo, lossReason, result);
            msg = result ? "永久挂失成功" : "永久挂失失败";
        } else {
            return ERROR;
        }
        model.addAttribute("msg", msg);
        model.addAttribute("result", result);
        return RESULT_URL;
    }

    /**
     * 取消临时挂失页面
     *
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "cancel")
    public String cancel(Model model, HttpServletRequest request) {
        String identityType = getGcsIdentityType(request);
        String identityNo = getIdentityNo(request);
        logger.info("@ReportLost@取消临时挂失查询卡列表，参数：[identityType={},identityNo={}]", identityType, identityNo);
        List<String> cardNoList = reportLostService.selectCardNoList(identityType, identityNo);
        logger.info("@ReportLost@取消临时挂失查询卡列表，参数：[identityType={},identityNo={}]，结果：[{}]", identityType, identityNo, cardNoList);
        if (cardNoList == null) {
            return BUSYURL;
        } else if (cardNoList.size() == 0) {
            return NOCARDURL;
        }
        try {
            Crypt.cardNoCrypt(cardNoList);
        } catch (Exception e) {
            logger.error("@ReportLost@加密卡列表出现异常" + e);
            return BUSYURL;
        }
        model.addAttribute("cardList", cardNoList);
        return CANCEL_URL;
    }

    /**
     * 取消临时挂失
     *
     * @param cardNo  卡号
     * @param model   Model
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "doCancel")
    public String doCancel(String cardNo, Model model, HttpServletRequest request) {
        String msg;
        boolean result;
        String identityType = getGcsIdentityType(request);
        String identityNo = getIdentityNo(request);
        try {
            cardNo = Crypt.decode(cardNo);
        } catch (Exception e) {
            logger.error("@ReportLost@解密卡号出现异常,[cardNo={}]", cardNo, e);
            return BUSYURL;
        }
        logger.info("@ReportLost@取消临时挂失，参数：[cardNo={},identityType={},identityNo={}]", cardNo, identityType, identityNo);
        result = reportLostService.relieveCreditCardTempReportLost(cardNo, identityType, identityNo);
        logger.info("@ReportLost@取消临时挂失，参数：[cardNo={},identityType={},identityNo={}]，结果：[{}]", cardNo, identityType, identityNo, result);
        msg = result ? "解除临时挂失成功" : "解除临时挂失失败";
        model.addAttribute("msg", msg);
        model.addAttribute("result", result);
        return RESULT_URL;
    }

    /**
     * 获取短信验证码
     *
     * @param request  HttpServletRequest
     * @param mobileNo 手机号
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "getMsgCode_ajax")
    public String getMsgCode_ajax(HttpServletRequest request, String mobileNo) {
        String identityType = getGcsIdentityType(request);
        String identityNo = getIdentityNo(request);
        logger.info("@ReportLost@挂失发送短信验证码，参数：[identityType={},identityNo={},mobileNo={}]", identityType, identityNo, mobileNo);
        String sendResult = reportLostService.sendSMS(identityType, identityNo, mobileNo);
        logger.info("@ReportLost@挂失发送短信验证码，参数：[identityType={},identityNo={},mobileNo={}]，结果：[{}]", identityType, identityNo, mobileNo, sendResult);
        return sendResult;
    }

    /**
     * 短信验证码验证
     *
     * @param request  HttpServletRequest
     * @param mobileNo 手机号
     * @param code     验证码
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "checkMsgCode_ajax")
    public String checkMsgCode_ajax(HttpServletRequest request, String mobileNo, String code) {
        String identityNo = getIdentityNo(request);
        logger.info("@ReportLost@挂失验证短信验证码，参数：[identityNo={},mobileNo={},code={}]", identityNo, mobileNo, code);
        String result = Boolean.toString(reportLostService.checkSMSCode(identityNo, mobileNo, code)).toLowerCase();
        logger.info("@ReportLost@挂失验证短信验证码，参数：[identityNo={},mobileNo={},code={}]，结果：[{}]", identityNo, mobileNo, code, result);
        return result;
    }

}
