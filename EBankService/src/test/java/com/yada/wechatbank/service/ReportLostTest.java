package com.yada.wechatbank.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by echo on 16/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class ReportLostTest {

    @Autowired
    private ReportLostService reportLostService;

    private String idType = "SSNO";
    private String idNo = "MOCK01";
    private String cardNo = "6225888899990001";
    private String entyMethod = "test";
    private String lostReason = "testlostReason";
    private String mobileNo = "18888888888";


    @Test
    public void testSelectCardNoList(){
        List<String> result = reportLostService.selectCardNoList(idType,idNo);
        Assert.assertNotNull(result);
    }

    @Test
    public void testTempCreditCardReportLost(){

        boolean result = reportLostService.tempCreditCardReportLost(cardNo,entyMethod,idType,idNo,lostReason);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testCreditCardReportLost(){
        boolean result = reportLostService.creditCardReportLost(cardNo,idType,idNo,lostReason);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testRelieveCreditCardTempReportLost(){
        boolean result = reportLostService.relieveCreditCardTempReportLost(cardNo,idType,idNo);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testSendSMS(){
        String result = reportLostService.sendSMS(idType,idNo,mobileNo);
        Assert.assertEquals("true",result);
    }

    @Test
    public void testCheckSMSCode(){
        boolean result = reportLostService.checkSMSCode(idNo,idType,"123456");
        Assert.assertEquals(false,result);
    }


}
