package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.WbicCardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 海淘卡Controller
 * Created by pangChangSong on 2016/4/21.
 */
@Controller
@RequestMapping("wbicCardInfo")
public class WbicCardInfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LISTURL = "wechatbank_pages/seawashes/list";
    private static final String APPLYURL = "wechatbank_pages/seawashes/apply";

    @Value("${seawashes.url}")
    private String seawashUrl;

    @Autowired
    private WbicCardInfoService wbicCardInfoServiceImpl;

    /**
     * 查询海淘卡
     *
     * @param model   model
     * @param request 请求对象
     * @return 返回视图
     */
    @RequestMapping("list")
    public String list(Model model, HttpServletRequest request) {
        //获得证件号和证件类型
        String idNum = getIdentityNo(request);
        String idType = getGcsIdentityType(request);
        //查询海淘卡
        String wbicCardNo = wbicCardInfoServiceImpl.getWbicCards(idNum, idType);
        logger.info("@WbicCardInfo根据idNum[{}],idType[{}]查询海淘信用卡信息，查询到的信用卡信息wbicCardNo[{}]", idNum, idType,
                wbicCardNo);
        //发生异常，跳到错误页面
        if (wbicCardNo == null) {
            return BUSYURL;
        }
        //没有申请卡片，跳到申请页面
        if (wbicCardNo.isEmpty()) {
            model.addAttribute("seawashUrl", seawashUrl);
            return APPLYURL;
        }
        //给海淘卡用户发送短信
        Boolean smsFlag = wbicCardInfoServiceImpl.wbicCardInfoSendSms(wbicCardNo);
        logger.info("@WbicCardInfo根据卡信息wbicCardNo[{}]发送短信,返回的结果为smsFlag[{}]", wbicCardNo, smsFlag);
        if (!smsFlag) {
            return BUSYURL;
        }
        return LISTURL;

    }

    /**
     * 海淘卡申请
     *
     * @param model model
     * @return 返回视图
     */
    @RequestMapping("cardApply")
    public String cardApply(Model model) {
        model.addAttribute("seawashUrl", seawashUrl);
        return APPLYURL;
    }
}
