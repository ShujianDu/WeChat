package com.yada.wechatweb.controller;

import com.yada.wechatweb.base.BaseController;
import com.yada.wechatweb.rmi.interfaces.IWCBInterface;
import com.yada.wechatweb.service.WcbOAuthEntranceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by Tx on 2016/5/9.
 */
@Controller
@RequestMapping(value = "wcboauthentrance")
public class WcbOAuthEntranceController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IWCBInterface wcbInterface;
    @Autowired
    private WcbOAuthEntranceService wcbOAuthEntranceService;

    @Value("${wx.subscribeurl}")
    private String SUBSCRIBEURL;

    /**
     * auth2.0验证入口
     * @param code              微信返回码
     * @param otherRedirectUrl  重定向URL
     */
    @RequestMapping(value = "reqCheck")
    public String reqCheck(String code,String otherRedirectUrl)
    {
        if(verificationString(code,otherRedirectUrl))
        {
            logger.info("非法请求，未通过正常渠道请求地址");
            return ERROR;
        }
        //通过服务平台获取openId
        String openId=wcbInterface.getOpenIdByCode(code);
        if(verificationString(openId))
        {
            logger.warn("通过code[{}]到服务平台获取用户openId失败",code);
            return ERROR;
        }
        //通过服务平台判断用户是否关注
        String checkSubscribe=wcbInterface.checkSubscribe(openId);
        if(verificationString(checkSubscribe))
        {
            logger.warn("通过openId[{}]到服务平台获取用户是否关注失败",openId);
            return ERROR;
        }
        //判断是否关注
        if ("0".equals(checkSubscribe))
        {
            return SUBSCRIBEURL;
        }

        //效验成功后生成随机码并保存数据
        String authCode=wcbOAuthEntranceService.saveAuthCode(openId);

        //判断链接是否带?
        if(otherRedirectUrl.indexOf("?")>0)
        {
            otherRedirectUrl= otherRedirectUrl+"&openId="+openId+"&authCode="+authCode;
        }else{
            otherRedirectUrl= otherRedirectUrl+"?openId="+openId+"&authCode="+authCode;
        }
        return "redirect:"+otherRedirectUrl;
    }

}
