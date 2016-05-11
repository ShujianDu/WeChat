package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BindingService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.IdTypeUtil;
import com.yada.wx.db.service.model.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.query.BindingQuery;
import com.yada.wechatbank.util.TokenUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 客户身份绑定、默认卡绑定
 *
 * @author zm
 */
//@Controller
//@RequestMapping(value = "binding")
public class BindingController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String BOUNDURL = "wechatbank_pages/Binding/bound";
    private static final String BINDLISTURL = "wechatbank_pages/Binding/binding";
    private static final String BINDCARDURL = "wechatbank_pages/Binding/bindingDefCard";
    private static final String SUCCESSURL = "wechatbank_pages/Binding/success";
    private static final String ERRORURL = "wechatbank_pages/Binding/error";
    private static final String LOCK = "wechatbank_pages/Binding/lock";
    private static final String FILLIDTYPEURL = "wechatbank_pages/Binding/fillIdType";
    //    @Autowired
    private BindingService bindingServiceImpl;
    //    @Autowired
    private SmsService smsServiceImpl;

    /**
     * 进入客户绑定界面
     *
     * @param bindingQuery 绑定查询实体
     * @param model        Model
     * @param request      HttpServletRequest
     */
    @RequestMapping(value = "list")
    public String list(
            @ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
            HttpServletRequest request) {
        //TODO 获取参数方式
//        String openId = (String) request.getAttribute("openId");
        String openId = request.getParameter("openId");
        if (openId != null && !"".equals(openId)) {
            bindingQuery.setOpenId(openId);
        }
        logger.info("@BD@从链接中获取到openId[{}]" + openId);
        boolean result = bindingServiceImpl.validateIsBinding(openId);
        // 判断是否已经绑定
        if (result) {
            model.addAttribute("openId", openId);
            return BOUNDURL;
        }
        model.addAttribute("model", bindingQuery);
        TokenUtil.addToken(request);
        return BINDLISTURL;
    }


    /**
     * 客户身份绑定
     *
     * @param bindingQuery 绑定查询实体
     * @param model        Model
     * @param request      HttpServletRequest
     */
    @RequestMapping(value = "bindingM")
    public String bindingM(
            @ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
            HttpServletRequest request) {
        if (bindingQuery.getOpenId() == null || "".equals(bindingQuery.getOpenId())) {
            return ERROR;
        }
        if (bindingServiceImpl.isLocked(bindingQuery.getOpenId(), bindingQuery.getIdNumber())) {
            logger.info("@BD@用户已锁定idNumber[{}]idType[{}]", bindingQuery.getIdNumber(), bindingQuery.getIdType());
            return LOCK;
        }
        String mobileCode = request.getParameter("mobileCode");
        //验证短信验证码
        if (!smsServiceImpl.checkSMSCode(bindingQuery.getIdNumber(), bindingQuery.getMobilNo(), "binding", mobileCode)) {
            model.addAttribute("msg", "3");
            model.addAttribute("model", bindingQuery);
            logger.info("@BD@短信验证码错误idNumber[{}]idType[{}]", bindingQuery.getIdNumber(), bindingQuery.getIdType());
            return BINDLISTURL;
        }
        //获取session中存储的短信验证码的证件号
        String smsIdNum = (String) request.getSession().getAttribute("idNum");
        //两次的证件号是否一致		
        if (!bindingQuery.getIdNumber().equals(smsIdNum)) {
            bindingServiceImpl.addCountCache(bindingQuery.getOpenId(), bindingQuery.getIdNumber());
            model.addAttribute("msg", "2");
            model.addAttribute("model", bindingQuery);
            logger.info("@BD@两次证件号不一致idNumber[{}]", bindingQuery.getIdNumber());
            return BINDLISTURL;
        }
        logger.info("@BD@用户提交的绑定数据openId[{}]idNumber[{}]idType[{}]mobilNo[{}]", bindingQuery.getOpenId(), bindingQuery.getIdNumber(), bindingQuery.getIdType(), bindingQuery.getMobilNo());
        String bindingResult = bindingServiceImpl.custBinding(bindingQuery.getOpenId(), bindingQuery.getIdType(),
                bindingQuery.getIdNumber(), bindingQuery.getPasswordQuery());
        // 查不到卡号
        if ("1".equals(bindingResult)) {
            model.addAttribute("model", bindingQuery);
            model.addAttribute("msg", bindingResult);
            logger.info("@BD@查不到卡号openId[{}]idNumber[{}]idType[{}]mobilNo[{}]", bindingQuery.getOpenId(), bindingQuery.getIdNumber(), bindingQuery.getIdType(), bindingQuery.getMobilNo());
        } else if ("2".equals(bindingResult)) { // 验密失败
            model.addAttribute("model", bindingQuery);
            model.addAttribute("msg", bindingResult);
            logger.info("@BD@验密失败openId[{}]idNumber[{}]idType[{}]mobilNo[{}]", bindingQuery.getOpenId(), bindingQuery.getIdNumber(), bindingQuery.getIdType(), bindingQuery.getMobilNo());
        } else if ("0".equals(bindingResult)) { // 成功
            // 获取卡列表
            List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(bindingQuery.getIdType(), bindingQuery.getIdNumber());
            // 返回值为空或没有数据
            if (cardList == null) {
                logger.warn("@BD@查不到卡号idNumber[{}]idType[{}]", bindingQuery.getIdNumber(), bindingQuery.getIdType());
                return BUSYURL;
            } else if (cardList.size() == 0) {
                logger.warn("@BD@查询到卡列表长度为0,idNumber[{}]idType[{}]", bindingQuery.getIdNumber(), bindingQuery.getIdType());
                return NOCARDURL;
            } else {
                //为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合
                List<String> cardListCrypt = new ArrayList<>();
                for (CardInfo cardInfo : cardList) {
                    String cardNo = cardInfo.getCardNo();
                    cardListCrypt.add(cardNo);
                }
                try {
                    Crypt.cardNoCrypt(cardListCrypt);
                } catch (Exception e) {
                    logger.error("@BD@加密卡列表失败,idNumber[{}]idType[{}]", bindingQuery.getIdNumber(), bindingQuery.getIdType());
                    return BUSYURL;
                }
                model.addAttribute("cardListCrypt", cardListCrypt);
                model.addAttribute("model", bindingQuery);
            }
            return BINDCARDURL;
        } else {
            return ERRORURL;
        }
        return BINDLISTURL;
    }

    /**
     * 默认卡绑定 需通过openId、authCode权限验证
     *
     * @param bindingQuery 绑定查询实体
     * @param model        Model
     * @param request      HttpServletRequest
     */
    @RequestMapping(value = "bindDefCard")
    public String bindDefCard(
            @ModelAttribute("formBean") BindingQuery bindingQuery, Model model,
            HttpServletRequest request) {
        String openId = request.getParameter("openId");
        if (openId == null || "".equals(openId)) {
            openId = (String) request.getAttribute("openId");
            bindingQuery.setOpenId(openId);
        }
        //查询数据库中的客户信息的证件类型有无数据
        if (bindingServiceImpl.isExistIdType(openId).get("isexist").equals("false")) {
            model.addAttribute("idNum", bindingServiceImpl.isExistIdType(openId).get("idNum"));
            model.addAttribute("model", bindingQuery);
            logger.info("@BD@用户未绑定证件类型,openId[{}]", openId);
            return FILLIDTYPEURL;
        }
        //获取默认卡列表
        String defCardNo = bindingServiceImpl.getDefCardNo(openId);
        if (defCardNo == null) {
            defCardNo = "";
        }

        CustomerInfo customerInfo = bindingServiceImpl.findCustomerInfoByOpenId(openId);
        if (customerInfo == null) {
            logger.warn("@BD@绑定默认卡,根据openId获取客户信息为空openId[{}]", openId);
            return BUSYURL;
        }
        List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(customerInfo.getIdentityType(), customerInfo.getIdentityNo());
        if (cardList == null) {
            logger.warn("@BD@绑定默认卡,根据openId获取客户信息为空openId[{}]", openId);
            return BUSYURL;
        } else if (cardList.size() == 0) {
            logger.warn("@BD@用户未绑定证件类型,openId[{}]", openId);
            return NOCARDURL;
        } else {
            //为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合
            List<String> cardListCrypt = new ArrayList<>();
            for (CardInfo cardInfo : cardList) {
                String cardNo = cardInfo.getCardNo();
                cardListCrypt.add(cardNo);
            }
            try {
                Crypt.cardNoCrypt(cardListCrypt);
            } catch (Exception e) {
                logger.error("@BD@加密卡列表异常,openId[{}]", openId);
                return BUSYURL;
            }
            model.addAttribute("defCardNo", defCardNo);
            model.addAttribute("cardListCrypt", cardListCrypt);
            model.addAttribute("model", bindingQuery);
        }
        return BINDCARDURL;
    }

    /**
     * 默认卡绑定 需通过身份验证码验证
     *
     * @param bindingQuery 绑定查询实体
     */
    @RequestMapping(value = "bindDefCardP")
    public String bindDefCardP(
            @ModelAttribute("formBean") BindingQuery bindingQuery) {
        String defCardNO = bindingQuery.getDefaultCard();
        try {
            defCardNO = Crypt.decode(defCardNO);
        } catch (Exception e) {
            logger.error("@BD@解密卡号出现错误openId[{}]defCardNO[{}]", bindingQuery.getOpenId(), defCardNO);
            return ERRORURL;
        }
        logger.info("@BD@用户提交的默认卡信息openId[{}]defCardNO[{}]", bindingQuery.getOpenId(), defCardNO);
        boolean dcbReturn = bindingServiceImpl.defCardBinding(bindingQuery.getOpenId(), defCardNO);
        //绑定默认卡并返回结果 失败进入绑定失败界面 成功进入绑定成功界面
        if (dcbReturn) {
            // 定义绑定成功跳转的url
            return SUCCESSURL;
        } else {
            logger.warn("@BD@默认卡绑定失败openId[{}]defCardNO[{}]", bindingQuery.getOpenId(), defCardNO);
            // 定义绑定失败跳转的url
            return ERRORURL;
        }

    }


    /**
     * 获取短信验证码
     *
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "getSMSCode_ajax")
    @ResponseBody
    public String getSMSCode_ajax(HttpServletRequest request) throws IOException {
        String result = "false";
        String identityType = request.getParameter("idType");
        String identityNo = request.getParameter("identityNo");
        String openId = request.getParameter("openId");
        // Session中存储的验证码
        String Randomcode_yz = (String) request.getSession().getAttribute(
                "jcmsrandomchar");
        String verificationCode = request.getParameter("verificationCode");
        String mobilNo = request.getParameter("mobilNo");
        if (verificationCode != null
                && verificationCode.equalsIgnoreCase(Randomcode_yz)) {
            if (!"".equals(identityNo) && !"".equals(mobilNo)) {
                logger.info("@BD@验证手机号码openId[{}]identityNo[{}]identityType[{}]mobilNo[{}]", openId, identityNo, identityType, mobilNo);
                //验证手机号
                String valResult = bindingServiceImpl.vaidateMobilNo(openId, identityNo, IdTypeUtil.numIdTypeTransformToECode(identityType), mobilNo);
                if ("true".equals(valResult)) {
                    logger.info("@BD@发送短信验证码openId[{}]identityNo[{}]identityType[{}]mobilNo[{}]", openId, identityNo, identityType, mobilNo);
                    //调用发送短信验证码方法
                    boolean sendResult = smsServiceImpl.sendBinDingSMS(identityNo, mobilNo, "binding");
                    if (!sendResult) {
                        logger.info("@BD@发送短信验证码失败openId[{}]identityNo[{}]identityType[{}]mobilNo[{}]", openId, identityNo, identityType, mobilNo);
                        result = "exception";
                    } else {
                        //验证码发送成功记录证件号，防止黑客修改卡号后再验证查询密码
                        request.getSession().setAttribute("idNum", identityNo);
                        TokenUtil.addToken(request);
                        result = sendResult + "," + request.getSession().getAttribute("keyInSession");
                    }
                } else {
                    logger.info("@BD@验证手机号码错误openId[{}]identityNo[{}]identityType[{}]mobilNo[{}]", openId, identityNo, identityType, mobilNo);
                    result = valResult;
                }
            }
        } else {
            logger.info("@BD@手机验证码错误openId[{}]identityNo[{}]identityType[{}]mobilNo[{}]", openId, identityNo, identityType, mobilNo);
            result = "errorCode";
        }
        return result;
    }

    @RequestMapping(value = "duty")
    public String duty() {
        return "wechatbank_pages/Binding/duty";
    }

    @RequestMapping(value = "fillIdentityType")
    public String fillIdentityType(@ModelAttribute("formBean") BindingQuery bindingQuery, Model model, HttpServletRequest request) {
        String openId = request.getParameter("openId");
        String identityNo = request.getParameter("idNumber");
        String identityType = request.getParameter("idType");
        if (!bindingServiceImpl.isCorrectIdentityType(identityNo, identityType)) {
            model.addAttribute("idNum", identityNo);
            model.addAttribute("model", bindingQuery);
            model.addAttribute("msg", "1");
            return FILLIDTYPEURL;
        }
        if (bindingServiceImpl.fillIdentityType(identityType, identityNo)) {
            // 获取默认卡
            String defCardNo = bindingServiceImpl.getDefCardNo(openId);
            if (defCardNo == null) {
                defCardNo = "";
            }
            logger.info("@BD@补充证件类型获取卡列表openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
            //获得卡号列表
            List<CardInfo> cardList = bindingServiceImpl.selectCardNOs(identityType, identityNo);
            //返回值为空或没有数据
            if (cardList == null) {
                logger.info("@BD@补充证件类型获取卡列表为null或没有数据openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                return BUSYURL;
            } else if (cardList.size() == 0) {
                logger.info("@BD@补充证件类型获取卡列表长度为0,openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                return NOCARDURL;
            } else {
                //为方便页面显示加密的卡号，为卡号单独设置传递到页面的卡号列表集合
                List<String> cardListCrypt = new ArrayList<>();
                for (CardInfo cardInfo : cardList) {
                    String cardNo = cardInfo.getCardNo();
                    cardListCrypt.add(cardNo);
                }
                try {
                    Crypt.cardNoCrypt(cardListCrypt);
                } catch (Exception e) {
                    logger.info("@BD@补充证件类型加密卡列表失败,openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                    return BUSYURL;
                }
                model.addAttribute("defCardNo", defCardNo);
                model.addAttribute("cardListCrypt", cardListCrypt);
                model.addAttribute("model", bindingQuery);
                model.addAttribute("cardList", cardList);
            }
            return BINDCARDURL;
        }
        return BUSYURL;
    }

    @RequestMapping(value = "locked")
    public String locked() {
        return LOCK;
    }
}
