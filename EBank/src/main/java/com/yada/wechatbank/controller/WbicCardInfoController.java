package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.WbicCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 海淘卡Controller
 * Created by pangChangSong on 2016/4/21.
 */
@Controller
@RequestMapping("wbicCardInfo")
public class WbicCardInfoController extends BaseController {

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
        String idType = getIdentityType(request);
        //查询海淘卡
        List<String> list = wbicCardInfoServiceImpl.getWbicCards(idNum, idType);
        //发生异常，跳到错误页面
        if (list == null) {
            return BUSYURL;
        }
        //没有申请卡片，跳到申请页面
        if (list.size() == 0) {
            model.addAttribute("seawashUrl", seawashUrl);
            return APPLYURL;
        }
        //给海淘卡用户发送短信,这里选取第一张卡
        Boolean smsFlag = wbicCardInfoServiceImpl.wbicCardInfoSendSms(list.get(0));
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
