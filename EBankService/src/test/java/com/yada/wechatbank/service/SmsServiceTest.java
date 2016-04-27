package com.yada.wechatbank.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 发送短信测试
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class SmsServiceTest {
    @Autowired
    private SmsService smsService;

    private String identityNo = "110625199301280000";
    private String mobileNo = "18888888888";
    private String bizCode = "Test_Code";

    @Test
    public void testAssemblySMS() {
        boolean result = smsService.sendSMS(identityNo, mobileNo, bizCode);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testCheckSMSCode() {
        boolean result = smsService.checkSMSCode(identityNo, mobileNo, bizCode, "888888");
        Assert.assertEquals(false, result);
    }

}
