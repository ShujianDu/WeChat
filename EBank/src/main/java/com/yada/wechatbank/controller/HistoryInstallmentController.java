package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.model.HistoryInstallmentList;
import com.yada.wechatbank.service.HistoryInstallmentService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.util.Crypt;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 历史分期付款查询
 *
 * @author zm
 */
@Controller
@RequestMapping(value = "historyInstalment")
public class HistoryInstallmentController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String LISTURL = "wechatbank_pages/HistoryInstallment/list";
    private static final String STARTNUM = "1";
    private static final String SELECTNUM = "10";

    @Autowired
    private HistoryInstallmentService historyInstallmentServiceImpl;

    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, Model model) {
        //获取卡片列表
        List<CardInfo> cardList = historyInstallmentServiceImpl.selectCardNOs(getIdentityType(request), getIdentityNo(request));
        //返回值为空或没有数据
        if (cardList == null) {
            logger.warn("@LSFQCX@获取卡列表为空identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        } else if (cardList.size() == 0) {
            logger.warn("@LSFQCX@获取卡列表长度为0identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return NOCARDURL;
        }
        List<String> cardListCrypt = new ArrayList<>();
        for (CardInfo cardInfo : cardList) {
            String cardNo = cardInfo.getCardNo();
            cardListCrypt.add(cardNo);
        }
        try {
            Crypt.cardNoCrypt(cardListCrypt);
        } catch (Exception e) {
            logger.error("@LSFQCX@加密卡列表异常identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        }

        model.addAttribute("cardListCrypt", cardListCrypt);
        return LISTURL;
    }

    @RequestMapping(value = "listP")
    public String listP(HttpServletRequest request, Model model) {
        String cardNo = request.getParameter("cardNo");
        List<CardInfo> cardList = historyInstallmentServiceImpl.selectCardNOs(getIdentityType(request), getIdentityNo(request));
        //返回值为空或没有数据
        if (cardList == null) {
            logger.warn("@LSFQCX@获取卡列表为空identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        } else if (cardList.size() == 0) {
            logger.warn("@LSFQCX@获取卡列表长度为0identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return NOCARDURL;
        }
        List<String> cardListCrypt = new ArrayList<>();
        for (CardInfo cardInfo : cardList) {
            cardListCrypt.add(cardInfo.getCardNo());
        }
        try {
            Crypt.cardNoCrypt(cardListCrypt);
        } catch (Exception e) {
            logger.error("@LSFQCX@加密卡列表异常identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
        }
        model.addAttribute("cardListCrypt", cardListCrypt);
        HistoryInstallmentList historyInstallmentList = null;
        try {
            historyInstallmentList = historyInstallmentServiceImpl.queryHistoryInstallment(Crypt.decode(cardNo), STARTNUM,
                    SELECTNUM);
        } catch (Exception e) {
            logger.error("@LSFQCX@解密卡号异常identityNo[{}]identityType[{}]cardNo[{}]", getIdentityNo(request), getIdentityType(request), cardNo);
        }
        if (historyInstallmentList == null || historyInstallmentList.getHistoryInstallmentList() == null) {
            logger.warn("@LSFQCX@查询到历史分期信息为空identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        }
        List<HistoryInstallment> list = historyInstallmentList.getHistoryInstallmentList();
        //返回值为空或没有数据
        if (list == null) {
            logger.error("@LSFQCX@从返回信息终获取到的信息列表为空identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        }
        model.addAttribute("pageList", list);
        model.addAttribute("cardNo", cardNo);
        model.addAttribute("isFollowUp", historyInstallmentList.isFollowUp());
        model.addAttribute("nextGCSStartIndex", Integer.parseInt(SELECTNUM) + 1);
        return LISTURL;
    }


    @RequestMapping(value = "ajax_getMore")
    @ResponseBody
    public String ajax_getMore(HttpServletRequest request) throws IOException {
        //返回结果
        String result;
        String isFollowUp = request.getParameter("isFollowUp");
        String nextGCSStartIndex = request.getParameter("nextGCSStartIndex");
        if ("false".equals(isFollowUp)) {
            result = "null";
        } else {
            HistoryInstallmentList historyInstallmentList;
            String cardNo = "";
            try {
                cardNo = Crypt.decode(request.getParameter("cardNo"));
            } catch (Exception e) {
                logger.error("@LSFQCX@解密卡号异常cardNo[{}]", request.getParameter("cardNo"));
            }
            List<HistoryInstallment> list;
            //调用后台查询分期历史
            historyInstallmentList = historyInstallmentServiceImpl.queryHistoryInstallment(cardNo, nextGCSStartIndex, SELECTNUM);
            if (historyInstallmentList == null || historyInstallmentList.getHistoryInstallmentList() == null) {
                logger.error("@LSFQCX@未查询到历史分期信息cardNo[{}]", request.getParameter("cardNo"));
                result = "exception";
            } else {
                list = historyInstallmentList.getHistoryInstallmentList();
                result = JSONArray.fromObject(list).toString();
            }
        }
        return result;
    }


}
