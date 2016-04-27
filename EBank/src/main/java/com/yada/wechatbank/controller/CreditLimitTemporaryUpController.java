package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.yada.wechatbank.model.CreditLimitTemporaryUpReview;
import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;
import com.yada.wechatbank.service.CreditLimitTemporaryUpService;
import com.yada.wechatbank.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.DateTimeUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 临时提升额度Controller
 *
 * @author Tx
 */
@Controller
@RequestMapping(value = "creditLimitTemporaryUp")
public class CreditLimitTemporaryUpController extends BaseController {
    private final static Logger logger = LoggerFactory
            .getLogger(CreditLimitTemporaryUpController.class);


    private static final String LISTURL = "wechatbank_pages/creditLimitTemporaryUp/list";
    private static final String TEMPORARYUPURL = "wechatbank_pages/creditLimitTemporaryUp/temporaryUp";
    private static final String SHOWHISTORY = "wechatbank_pages/creditLimitTemporaryUp/showHistory";
    private static final String SUCCESS = "wechatbank_pages/creditLimitTemporaryUp/success";
    private static final String ERRORURL = "wechatbank_pages/error";
    private static final String FAILURL = "wechatbank_pages/creditLimitTemporaryUp/fail";
    private static final String BIZ_CODE="CreditLimitTemporaryUp";

    @Autowired
    private CreditLimitTemporaryUpService creditLimitTemporaryUpService;
    @Autowired
    private SmsService smsService;
    /**
     * 临时提额主页面
     */
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, Model model) {
        String identityNo = getIdentityNo(request);
        String identityType = getIdentityType(request);
        List<String> cardList = creditLimitTemporaryUpService.getProessCardNoList(identityType, identityNo);
        if (cardList == null || cardList.size() == 0) {
            return BUSYURL;
        }
        model.addAttribute("cardList", cardList);
        return LISTURL;
    }

    /**
     * 显示申请提升临时额度填写信息页面
     */
    @RequestMapping(value = "show")
    public String show(HttpServletRequest request,
                       String cardNo, Model model) {
        String identityNo = getIdentityNo(request);
        String identityType = getIdentityType(request);
        // 测评结果
        CreditLimitTemporaryUpReview amounts;
        try {
            cardNo = Crypt.decode(cardNo);
            amounts = creditLimitTemporaryUpService.getAmount(identityType, identityNo, cardNo);
            cardNo = Crypt.cardNoOneEncode(cardNo);
        } catch (Exception e) {
            logger.info("@LSTE@卡号解密出现错误cardNo:[" + cardNo + "]" + e);
            return ERRORURL;
        }
        if (amounts == null) {
            logger.info("@LSTE@额度测评结果返回为空  cardNo:[{}]", cardNo);
            return ERRORURL;
        }
        // 可以提升的额度值
        String amount = amounts.getAmount();
        if (amount == null || "".equals(amount)) {
            logger.info("@LSTE@用户卡cardNo[{}]可提升的额度返回为null", cardNo);
            return ERRORURL;
        }
        if ("B".equals(amounts.getPrincipalResultID())) {
            logger.info("@LSTE@额度测评接口返回（拒绝） B,cardNo:[{}]", cardNo);
            model.addAttribute("failMsg", "很抱歉，您目前暂不符合我行的临时额度提升条件！");
            return FAILURL;
        }
        model.addAttribute("amount", amount);
        // 生效日期
        String effectiveDate = DateTimeUtil.getDate();
        // 失效最小日期
        String minExpirationDate = DateTimeUtil.getAfterDate(1);
        // 失效最大日期
        String maxExpirationDate = DateTimeUtil.getAfterDate(60);
        model.addAttribute("minExpirationDate", minExpirationDate);
        model.addAttribute("maxExpirationDate", maxExpirationDate);
        model.addAttribute("effectiveDate", effectiveDate);
        model.addAttribute("cardNo", cardNo);
        model.addAttribute("cardStyle", amounts.getCardStyle());
        model.addAttribute("issuingBranchId", amounts.getIssuingBranchId());
        model.addAttribute("pmtCreditLimit", amounts.getPmtCreditLimit());
        return TEMPORARYUPURL;
    }

    /**
     * 申请提升临时额度提交审核
     */
    @RequestMapping(value = "temporaryUp")
    public String temporaryUp(HttpServletRequest request, String cardNo,
                              Model model, String eosPreAddLimit, String eosStarLimitDate,
                              String eosEndlimitdate, String cardStyle, String issuingBranchId, String pmtCreditLimit) {
        try {
            cardNo = Crypt.decode(cardNo);
        } catch (Exception e) {
            logger.info("@LSTE@卡号解密出现错误cardNo:[" + cardNo + "]" + e);
            return ERRORURL;
        }
        String identityNo = getIdentityNo(request);
        String identityType = getIdentityType(request);

        // 提交申请信息
        boolean result = creditLimitTemporaryUpService.temporaryUpCommit(
                identityType, identityNo, cardNo, eosPreAddLimit,
                eosStarLimitDate, eosEndlimitdate, cardStyle,
                issuingBranchId, pmtCreditLimit);
        if (!result) {
            logger.info("@LSTE@cardNo[{}]额度提升授权失败结果：[{}]", cardNo, false);
            model.addAttribute("failMsg", "很抱歉，提交申请失败！");
            return FAILURL;
        }
        return SUCCESS;
    }

    /**
     * 查询临时提升额度历史信息
     */
    @RequestMapping(value = "showHistory")
    public String queryHistory(String cardNo, Model model,
                               HttpServletRequest request) {
        String decodeCardNo;
        try {
            decodeCardNo = Crypt.decode(cardNo);
        } catch (Exception e) {
            logger.info("@LSTE@卡号解密出现错误cardNo:[" + cardNo + "]" + e);
            return ERRORURL;
        }
        // 查询临时额度提升历史信息
        String identityNo = getIdentityNo(request);
        String identityType = getIdentityType(request);
        List<CreditLimitTemporaryUpStatus> creditLimitTemporaryUpStatusList = creditLimitTemporaryUpService.getLimitUpHistory(
                identityType, identityNo, decodeCardNo);
        if (creditLimitTemporaryUpStatusList == null || creditLimitTemporaryUpStatusList.size() == 0) {
            logger.info("@LSTE@未查询到cardNo[{}]的历史额度提升信息，返回为null", cardNo);
            model.addAttribute("failMsg", "未查询到您近期的申请记录！");
            return FAILURL;
        }
        model.addAttribute("cardNo", decodeCardNo);
        model.addAttribute("limitUpHistorys", creditLimitTemporaryUpStatusList);
        return SHOWHISTORY;
    }

    /**
     * 发送短信验证码
     */
    @RequestMapping(value = "getMsgCode_ajax")
    @ResponseBody
    public String getMsgCode_ajax(HttpServletRequest request) {
        String result ;
        String identityNo=getIdentityNo(request);
        String identityType=getIdentityType(request);
        String mobileNo = creditLimitTemporaryUpService.getCustMobileNo(identityType, identityNo);
        String sendResult =  Boolean.toString(smsService.sendCreditLimitTemporaryUpSMS(identityNo, mobileNo, BIZ_CODE)).toLowerCase();
        logger.info("@LSTE@向用户[{}]发送短信验证码，发送结果[{}]",identityNo,sendResult);
       if ("true".equals(sendResult)) {
            result = "true";
        } else {
            result = "false";
        }
        return result;
    }

    /**
     * 验证短信验证码
     */
    @RequestMapping(value = "checkMagCode_ajax")
    @ResponseBody
    public String checkMagCode_ajax(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        String result ;
        String identityNo=getIdentityNo(request);
        String identityType=getIdentityType(request);
        String mobileNo = creditLimitTemporaryUpService.getCustMobileNo(identityType, identityNo);
        String sendResult = Boolean.toString(smsService.checkSMSCode(identityNo, mobileNo, BIZ_CODE, code)).toLowerCase();
        if ("true".equals(sendResult)) {
            result = "true";
        } else {
            result = "false";
        }
       return result;
    }
}
