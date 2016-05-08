package com.yada.wechatbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yada.wechatbank.model.*;
import com.yada.wechatbank.query.PointsQuery;
import com.yada.wechatbank.service.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.util.JsMapUtil;

/**
 * 积分明细Controller
 *
 * @author zm
 */
@Controller
@RequestMapping(value = "points")
public class PointsController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String LISTURL = "wechatbank_pages/Points/myPoints";
    private static final String DETAIL = "wechatbank_pages/Points/pointsDetails";
    private static final String VALIDATEURL = "wechatbank_pages/Points/pointsPeriod";
    private static final String EXCHANGE = "wechatbank_pages/Points/pointsExchange";
    @Autowired
    private PointsService pointsServiceImpl;
    @Value("${jf.ExchangeUrl}")
    private String exchangeUrl;


    @RequestMapping(value = "list")
    public String list(@ModelAttribute("formBean") PointsQuery pointsQuery, HttpServletRequest request, Model model) {
        request.getSession().setAttribute("menuId", "3");
        //调用后台获取积分余额
        PointsBalance pointsBalance = pointsServiceImpl.getPointsBlance(getIdentityType(request), getIdentityNo(request));
        model.addAttribute("pointsBalance", pointsBalance);
        return LISTURL;
    }

    /**
     * 积分明细列表
     *
     * @param pointsQuery 积分查询实体
     * @param request     HttpServletRequest
     * @param model       Model
     */
    @RequestMapping(value = "pointsDetail")
    public String pointsDetail(@ModelAttribute("formBean") PointsQuery pointsQuery, HttpServletRequest request, Model model) {
        //获取积分明细列表
        List<PointsDetail> pointsDetailList = pointsServiceImpl.getPointsDetail(getIdentityType(request), getIdentityNo(request));
        List<List<PointsDetail>> newList = new ArrayList<>();
        //返回值为空或没有数据
        if (pointsDetailList == null || pointsDetailList.size() == 0) {
            logger.warn("@JFMX@从后台获取积分明细未空或列表长度未0,identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        } else if (pointsDetailList.size() > 0 && pointsDetailList.get(0).getCardNo() != null && pointsDetailList.get(0).getId() != null) {
            newList = pointsServiceImpl.getList(pointsDetailList);
        }
        model.addAttribute("newList", newList);
        model.addAttribute("model", pointsQuery);
        return DETAIL;
    }

    /**
     * 积分到期日
     *
     * @param pointsQuery 积分查询实体
     * @param request     HttpServletRequest
     * @param model       Model
     */
    @RequestMapping(value = "validate")
    public String validate(@ModelAttribute("formBean") PointsQuery pointsQuery, HttpServletRequest request, Model model) {
        Integer numberP = Integer.parseInt(request.getParameter("numberP"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        //获取积分明细列表
        List<PointsDetail> pointsDetailList = pointsServiceImpl.getPointsDetail(getIdentityType(request), getIdentityNo(request));
        List<List<PointsDetail>> newList = new ArrayList<>();
        //返回值为空或没有数据
        if (pointsDetailList == null) {
            logger.warn("@JFXQ@从后台获取积分明细未空或列表长度未0,identityNo[{}]identityType[{}]", getIdentityNo(request), getIdentityType(request));
            return BUSYURL;
        } else if (pointsDetailList.size() > 0 && pointsDetailList.get(0).getCardNo() != null && pointsDetailList.get(0).getId() != null) {
            newList = pointsServiceImpl.getList(pointsDetailList);
        }
        PointsDetail pointsDetail = newList.get(numberP).get(number);
        //获取积分到期日明细列表
        List<PointsValidates> pointsValidatesList = pointsServiceImpl.getPointsValidates(pointsDetail.getCardNo());
        //返回值为空或没有数据
        if (pointsValidatesList == null) {
            return BUSYURL;
        }
        model.addAttribute("pointsValidatesList", pointsValidatesList);
        return VALIDATEURL;
    }

    /**
     * 积分兑换
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "pointsExchange")
    public String pointsExchange(HttpServletRequest request, Model model) {
        String cardNo = pointsServiceImpl.getCardNo(getIdentityType(request), getIdentityNo(request));
        if (cardNo == null) {
            cardNo = "1111111111111111";
        }
        VerificationCardNoResult verificationCardNoResult = pointsServiceImpl.verificationCardNo(cardNo);
        if (verificationCardNoResult != null) {
            model.addAttribute("sign", verificationCardNoResult.getSign());
            model.addAttribute("cardNo", verificationCardNoResult.getEncryptCardNo());
            model.addAttribute("exchangeUrl", exchangeUrl);
            return EXCHANGE;
        }
        return ERROR;
    }
}
