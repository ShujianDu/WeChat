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

import java.util.Random;


/**
 * 微信AUTH2.0入口
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
    private static final String AUTHAUTHFAILED = "wechatbank_pages/authFailed";
    //测试
    private String[] openIdArray = {"oi_0ms-AvZOlfdsRbjaGH0X7Pr9U", "oi_0msyJcP5iTxfPMwcIhIMoVglg", "oi_0ms5F2ABp2hgJxFTH3WlDs1WI", "oi_0msz1s-6aIVh7-pHv4TXGSZ9k", "oFDatjpd8-RY-O__qTWgVW1fhh6w", "oFDatjrsA9evjHK8WEEc_KgVCBio"};
    Random random = new Random();

    /**
     * auth2.0验证入口
     *
     * @param code        微信返回码
     * @param redirectUrl 重定向URL
     */
    @RequestMapping(value = "reqCheck")
    public String reqCheck(String code, String redirectUrl) {
        if (verificationString(code, redirectUrl)) {
            logger.info("非法请求，未通过正常渠道请求地址");
            return ERROR;
        }
        //通过服务平台获取openId
        String openId = wcbInterface.getOpenIdByCode(code);
        if (verificationString(openId)) {
            logger.info("通过code[{}]到服务平台获取用户openId失败", code);
            return AUTHAUTHFAILED;
        }
        //通过服务平台判断用户是否关注
        String checkSubscribe = wcbInterface.checkSubscribe(openId);
        if (verificationString(checkSubscribe)) {
            logger.warn("通过openId[{}]到服务平台获取用户是否关注失败", openId);
            return AUTHAUTHFAILED;
        }
        //判断是否关注
        if ("0".equals(checkSubscribe)) {
            return SUBSCRIBEURL;
        }
        //效验成功后生成随机码并保存数据
        String authCode = wcbOAuthEntranceService.saveAuthCode(openId);


        if (verificationString(authCode)) {
            logger.warn("通过openId[{}]到生成并保存authCode失败", openId);
            return AUTHAUTHFAILED;
        }

        //判断链接是否带?
        if (redirectUrl.indexOf("?") > 0) {
            redirectUrl = redirectUrl + "&authCode=" + authCode;
        } else {
            redirectUrl = redirectUrl + "&authCode=" + authCode;
        }
        return "redirect:" + redirectUrl;
    }


    /**
     * auth2.0验证入口测试地址
     *
     * @param redirectUrl 重定向URL
     */
    @RequestMapping(value = "reqCheckTest")
    public String reqCheckTest(String redirectUrl) {
        String openId = openIdArray[random.nextInt(5)];
        //效验成功后生成随机码并保存数据
        String authCode = wcbOAuthEntranceService.saveAuthCode(openId);
        //判断链接是否带?
        if (redirectUrl.indexOf("?") > 0) {
            redirectUrl = redirectUrl + "&authCode=" + authCode;
        } else {
            redirectUrl = redirectUrl + "?authCode=" + authCode;
        }
        return "redirect:" + redirectUrl;
    }


}
