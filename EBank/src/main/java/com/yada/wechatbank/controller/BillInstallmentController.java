package com.yada.wechatbank.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.BillCost;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;

/**
 * 账单分期
 * @author Tx
 */
@Controller
@RequestMapping(value = "billinstallment")
public class BillInstallmentController extends BaseController {

    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    private static final String SEARCH = "wechatbank_pages/BillInstallment/searchByCardNo";
    private static final String BILL_COST = "wechatbank_pages/BillInstallment/billCost";

    @Autowired
    private BillInstallmentService billInstallmentServiceImpl;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "list")
    public String list() {
        return "wechatbank_pages/BillInstallment/first";
    }

    /**
     * 跳转至
     */
    @RequestMapping(value = "search")
    public String search(HttpServletRequest request,Model model) {
        String identityNo = getIdentityNo(request);
        String identityType = getIdentityType(request);
        List<CardInfo> cardList = billInstallmentServiceImpl.getProessCardNoList(identityType, identityNo);
        logger.debug("@ZDFQ@根据证件类型[{}]证件号[{}]获取卡片列表,获取到的卡列表cardList[{}]", identityType, identityNo, cardList);
        // RMI返回值为空或没有数据
        if (cardList == null) {
            logger.warn("@ZDFQ@根据证件类型[{}]证件号[{}]获取到的卡列表为null", identityType, identityNo);
            return BUSYURL;
        } else if (cardList.size() == 0) {
            logger.warn("@ZDFQ@根据证件类型[{}]证件号[{}]获取到的卡列表长度为0", identityType, identityNo);
            return NOCARDURL;
        } else {
            model.addAttribute("cardList", cardList);
            return SEARCH;
        }

    }

    @RequestMapping(value = "searchResult")
    public String searchResult(HttpServletRequest request, Model model)
            throws ParseException {
        String cardNo = "";
        try {
            cardNo = Crypt.decode(request.getParameter("cardNo"));
        } catch (Exception e) {
            logger.warn("@ZDFQ@通过前台获取到的加密卡号解密时出现错误[" + cardNo + "]:" + e);
        }
        String currencyCode = request.getParameter("currencyCode");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date curDate = sdf.parse(getDate());
        // 根据卡号获取当期账单
        BillingSummary billingSummary = billInstallmentServiceImpl.getCurrentPeriodBill(cardNo);
        if (billingSummary == null) {
            logger.info("@ZDFQ@接收到账单列表为null,cardNo[{}]", cardNo);
            return BUSYURL;
        }

        String code = billingSummary.getCurrencyCode();
        if ("".equals(billingSummary.getPaymentDueDate())
                || billingSummary.getPaymentDueDate() == null
                || billingSummary.getPeriodEndDate() == null
                || "".equals(billingSummary.getPeriodEndDate())) {
            return BUSYURL;
        }
        Date periodEndDate = transDate(billingSummary.getPeriodEndDate(),
                0);
        Date paymentDueDate = transDate(
                billingSummary.getPaymentDueDate(), 2);
        if (!(code.equals(currencyCode) && periodEndDate.before(curDate)
                && paymentDueDate.after(curDate))) {
            model.addAttribute("status", "noBill");// 页面标识，页面根据该值判断，显示错误信息
            try {
                model.addAttribute("cardNo", Crypt.cardNoOneEncode(cardNo));
            } catch (Exception e) {
                logger.info("@ZDFQ@加密卡号失败cardNo[{}] e:{}", cardNo, e.getMessage());
            }
            return SEARCH;
        }

        logger.info("@ZDFQ@计算账单分期金额上下限，参数：cardNo[{}]currencyCode[{}]" ,cardNo, currencyCode);

        AmountLimit amountLimit = billInstallmentServiceImpl.getAmountLimit(cardNo, currencyCode);
        if (amountLimit == null) {
            logger.info("@ZDFQ@根据cardNo[{}],currencyCode[{}]获取账单分期金额上下限,获取账单分期金额上下限为null",cardNo,currencyCode);
            return BUSYURL;
        } else {
            if ("+ES10403".equals(amountLimit.getRespCode())) {
                logger.info("@ZDFQ@根据cardNo[{}],currencyCode[{}]获取账单分期金额上下限,"
                        + "获取账单分期金额上下限amountLimit[{}]",cardNo,currencyCode,amountLimit.toString());
                // 页面标识，提示用户账单不满足
                model.addAttribute("status", "notSatisfied");
                try {
                    model.addAttribute("cardNo", Crypt.cardNoOneEncode(cardNo));
                } catch (Exception e) {
                    logger.info("@ZDFQ@卡号加密出现异常[" + cardNo
                            + "]:" + e);
                }
                return SEARCH;
            } else if ("Exception".equals(amountLimit.getRespCode())) {
                logger.info("@ZDFQ@查询账单分期金额上下限，GCS返回错误信息");
                return BUSYURL;
            } else if (amountLimit.getRespCode() == null
                    || "".equals(amountLimit.getRespCode())) {
                try {
                    model.addAttribute("amountLimit", amountLimit);
                    model.addAttribute("cardNo", Crypt.cardNoOneEncode(cardNo));
                } catch (Exception e) {
                    logger.info("@ZDFQ@卡号加密失败cardNo[{}] e[{}]",cardNo,e.getMessage());
                    return ERROR;
                }
                model.addAttribute("currencyCode",
                        amountLimit.getCurrencyCode());
                return "wechatbank_pages/BillInstallment/searchRes";
            } else {
                return BUSYURL;
            }
        }
    }


    /**
     * 账单分期试算.do
     */
    @RequestMapping(value = "billCost")
    public String billCost(HttpServletRequest request, Model model) {
        // 币种
        String currencyCode = request.getParameter("currencyCode");
        // 账单分期下限金额
        String billLowerAmount = request.getParameter("billLowerAmount");
        // 账单分期实际金额
        String billActualAmount = request.getParameter("billActualAmount");
        // 分期期数
        String installmentsNumber = request.getParameter("installmentsNumber");
        // 账户ID
        String accountId = request.getParameter("accountId");
        // 账户号
        String accountNo = request.getParameter("accountNo");
        // 手续费收取方式 1：分期收取，0一次性收取
        String feeInstallmentsFlag = request
                .getParameter("feeInstallmentsFlag");
        String cardNo = "";
        try {
            cardNo = Crypt.decode(request.getParameter("cardNo"));
        } catch (Exception e) {
            logger.info("@ZDFQ@卡号解密出现错误cardNo[{}] e[{}]",cardNo,e.getMessage());
            return ERROR;
        }
        BillCost billCost = billInstallmentServiceImpl.getBillCost(accountId,accountNo, currencyCode,
                billLowerAmount, billActualAmount, installmentsNumber,
                feeInstallmentsFlag);
        if (billCost == null) {
            logger.warn("@ZDFQ@根据cardNo[{}],currencyCode[{}],billActualAmount[{}],installmentsNumber[{}]对账单分期进行试算,"
                    + "账单分期试算结果billCost为null",cardNo, currencyCode,
                    billLowerAmount,installmentsNumber);
            return BILL_COST;
        }
        logger.debug("@ZDFQ@根据cardNo[{}],currencyCode[{}],billActualAmount[{}],installmentsNumber[{}]对账单分期进行试算,"
                + "账单分期试算结果billCost[{}]", cardNo, currencyCode, billLowerAmount, installmentsNumber, billCost.toString());
        model.addAttribute("billCost", billCost);
        model.addAttribute("currencyCode", currencyCode);
        try {
            model.addAttribute("cardNo", Crypt.cardNoOneEncode(cardNo));
        } catch (Exception e) {
            logger.info("@WDZD@卡号加密出现异常cardNo[{}],e[{}]",cardNo, e.getMessage());
            return ERROR;
        }
        model.addAttribute("accountId", accountId);
        model.addAttribute("accountNo", accountNo);
        model.addAttribute("billActualAmount", billActualAmount);
        model.addAttribute("billLowerAmount", billLowerAmount);
        model.addAttribute("installmentsNumber", installmentsNumber);
        return BILL_COST;
    }

    /**
     * 账单分期授权.do
     */
    @RequestMapping(value = "installment")
    public String installment(HttpServletRequest request) {
        String cardNo = "";
        try {
            cardNo = Crypt.decode(request.getParameter("cardNo"));
        } catch (Exception e) {
            logger.info("@WDZD@cardNo crypt error,cardNo[" + cardNo + "]:" + e);
        }
        String currencyCode = request.getParameter("currencyCode");
        String billLowerAmount = request.getParameter("billLowerAmount");
        String installmentsNumber = request.getParameter("installmentsNumber");
        String billActualAmount = request.getParameter("billActualAmount");
        String accountId = request.getParameter("accountId");
        String accountNo = request.getParameter("accountNo");
        // 分期手续费收取方式
        String feeInstallmentsFlag = request
                .getParameter("feeInstallmentsFlag");
        boolean res = billInstallmentServiceImpl.billInstallment(accountId,accountNo,cardNo, currencyCode,
                billLowerAmount, billActualAmount, installmentsNumber,
                feeInstallmentsFlag);
        logger.debug("@ZDFQ@根据cardNo[{}],currencyCode[{}],billLowerAmount[{}],billActualAmount[{}],installmentsNumber["
        +"{}],feeInstallmentsFlag[{}]对账单分期进行分期,账单分期结果res[{}]",cardNo,currencyCode,billLowerAmount,billActualAmount,installmentsNumber,
                feeInstallmentsFlag,res );
        if (res) {
            return "wechatbank_pages/BillInstallment/success";
        } else {
            return ERROR;
        }
    }

    @RequestMapping(value = "getMsgCode_ajax")
    public void getMsgCode_ajax(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        boolean result = false;
        // Session中存储的验证码
        String Randomcode_yz = (String) request.getSession().getAttribute(
                "jcmsrandomchar");
        String verificationCode = request.getParameter("verificationCode");
        String mobilNo = request.getParameter("mobilNo");
        String identityNo=getIdentityNo(request);
        if (verificationCode != null
                && verificationCode.equalsIgnoreCase(Randomcode_yz)) {
            result = smsService.sendInstallmentSMS(identityNo, mobilNo, "BillInstallment");
            logger.debug("@ZDFQ@根据identityNo[{}],手机号[{}]发送账单分期验证短信验证码,"
                    + "发送结果sendResult[{}]",identityNo,mobilNo,result);
        }
        response.getWriter().print(result);
        response.getWriter().flush();
    }

    @RequestMapping(value = "checkMagCode_ajax")
    public void checkMagCode_ajax(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {

        String identityNo=getIdentityNo(request);
        String mobile = request.getParameter("mobile");
        String code = request.getParameter("code");
        boolean sendResult = smsService.checkSMSCode(identityNo,mobile,"BillInstallment", code);
        logger.debug("@ZDFQ@identityNo[{}],手机号[{}],code[{}"
                + "]验证账单分期短信验证码,验证结果sendResult[{}]",identityNo,mobile,code);
        response.getWriter().print(sendResult);
        response.getWriter().flush();
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -0);
        return new SimpleDateFormat("yyyyMMdd").format(calendar
                .getTime());
    }

    public static Date transDate(String logDate, int num) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        Date date;
        try {
            date = sdf.parse(logDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期转换异常", e);
        }
        cal.setTime(date);
        cal.add(Calendar.DATE, -num);
        String logBeforeDate = sdf.format(cal.getTime());
        return sdf.parse(logBeforeDate);
    }

}
