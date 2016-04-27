package com.yada.wechatbank.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 海淘卡Service测试
 * Created by pangChangSong on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class WbicCardInfoServiceTest {

    @Autowired
    private WbicCardInfoService wbicCardInfoService;

    /**
     * 查询海淘卡，返回正常
     */
    @Test
    public void testGetWbicCardsBackNormal(){
        String idNum = "110625199301280000";
        String idType = "01";
        String cardNo = wbicCardInfoService.getWbicCards(idNum, idType);
        Assert.assertNotNull(cardNo);
    }

    /**
     * 查询海淘卡，返回Null
     */
    @Test
    public void testGetWbicCardsBackNull(){
        String idNum = "110625199301280000";
        String idType = "02";
        String cardNo = wbicCardInfoService.getWbicCards(idNum, idType);
        Assert.assertNull(cardNo);
    }

    /**
     * 海淘卡发生短信，返回true
     */
    @Test
    public void testWbicCardInfoSendSmsBackTrue(){
        String cardNo = "11111111111111111111";
        boolean smsFlag = wbicCardInfoService.wbicCardInfoSendSms(cardNo);
        Assert.assertTrue(smsFlag);
    }

    /**
     * 海淘卡发生短信，返回false
     */
    @Test
    public void testWbicCardInfoSendSmsBackFalse(){
        String cardNo = "11111111111111111112";
        boolean smsFlag = wbicCardInfoService.wbicCardInfoSendSms(cardNo);
        Assert.assertEquals(false, smsFlag);
    }
}
