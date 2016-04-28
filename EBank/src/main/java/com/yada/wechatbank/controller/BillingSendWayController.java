package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yada.wechatbank.model.BillSendType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.BillingSendWayService;
import com.yada.wechatbank.util.Crypt;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账单寄送方式
 *
 * @author Tx
 */
@Controller
@RequestMapping(value = "billingsendway")
public class BillingSendWayController extends BaseController {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());
    private static final String LISTURL = "wechatbank_pages/BillingSendWay/list";
    private static final String EDITURL = "wechatbank_pages/BillingSendWay/edit";
    private static final String SUCCESS = "wechatbank_pages/BillingSendWay/success";

    @Autowired
    private BillingSendWayService billingSendWayServiceImpl;

    /**
     * 寄送方式查询
     */
    @RequestMapping(value = "list")
    public String list(Model model) {
        String identityType = "";
        String identityNo = "";
        List<BillSendType> list = billingSendWayServiceImpl.getBillSendType(identityType, identityNo);
        logger.info("@BillingSendWay@通过identityType[{}],identityNo[{}]获取账单寄送方式集合为[{}]", identityType, identityNo, list);
        if (list == null || list.size() == 0) {
            logger.warn("@BillingSendWay@通过identityType[{}],identityNo[{}]获取寄送方式集合为空或没有数据", identityType, identityNo);
            return BUSYURL;
        } else {
            model.addAttribute("sendTypeList", list);
        }
        return LISTURL;
    }

    /**
     * 进入寄送方式编辑页面
     */
    @RequestMapping(value = "edit")
    public String edit(Model model, String cardNo, String billSendType) {
        try {
            cardNo = Crypt.decode(cardNo);
        } catch (Exception e) {
            logger.error("@BillingSendWay@解密卡号出现异常cardNo[" + cardNo + "]", e);
            return ERROR;
        }
        BillSendType b = new BillSendType();
        b.setBillSendType(billSendType);
        b.setCardNo(cardNo);
        try {
            billingSendWayServiceImpl.processShowCardNo(b);
        } catch (Exception e) {
            logger.error("@ZDJSFSCX@卡号加密过程中出现错误cardNo["
                    + cardNo + "]:", e);
             return ERROR;
        }
        model.addAttribute("bsw", b);
        return EDITURL;
    }

    /**
     * 寄送方式编辑保存
     */
    @RequestMapping(value = "update")
    @ResponseBody
    public String update(HttpServletRequest request) {
        String cardNo = "";
        try {
            cardNo = Crypt.decode(request.getParameter("cardNo"));
        } catch (Exception e) {
            logger.error("@BillingSendWay@解密卡号出现异常cardNo[" + cardNo + "]", e);
            return "修改失败";
        }
        String billSendType = request.getParameter("billSendType");
        if (cardNo != null && !"".equals(cardNo) && billSendType != null
                && !"".equals(billSendType)) {
            logger.info("@BillingSendWay@卡cardNo[{}]修改账单寄送方式为[{}]",cardNo,billSendType);
            boolean result = billingSendWayServiceImpl.updateBillSendType(cardNo, billSendType);
            return result?"修改成功":"修改失败";
        }
        return "修改失败";
    }

    @RequestMapping(value = "success")
    public String success() {
        return SUCCESS;
    }
}
