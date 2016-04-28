package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * 绑定测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BindingServiceTest {

    @Autowired
    private BindingService bindingService;

    private String openId;
    private String idType;
    private String idNo;
    private String pwd;
    private String mobileNo;


    @Before
    public void init(){
        this.openId="123456";
        this.idType = "01";
        this.idNo = "12345678912234";
        this.pwd = "111111";
        this.mobileNo = "18888888888";
    }

    @Test
    public void testValidateIsBinding(){
        boolean result = bindingService.validateIsBinding(openId);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCustBinding(){
        String result = bindingService.custBinding(openId,idType,idNo,pwd);
        Assert.assertEquals("0",result);
    }

    @Test
    public void testIsExistIdType(){
        Map<String, String> result = bindingService.isExistIdType("111");
        String res = result.get("isexist");
        Assert.assertEquals("false",res);
    }

    @Test
    public void testGetDefCardNo(){
        String result = bindingService.getDefCardNo(openId);
        Assert.assertNull(result);
    }

    @Test
    public void testDefCardBinding(){
        boolean result = bindingService.defCardBinding(openId,idNo);
        Assert.assertEquals(false,result);
    }

    @Test
    public void testVaidateMobilNo(){
        String result = bindingService.vaidateMobilNo(openId,idNo,idType,mobileNo);
        Assert.assertEquals("true",result);
    }

    @Test
    public void testIsCorrectIdentityType(){
        boolean result = bindingService.isCorrectIdentityType(idNo,idType);
        Assert.assertEquals(false,result);
    }

    @Test
    public void testFillIdentityType(){
        boolean result = bindingService.fillIdentityType(idType,idNo);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testSelectCardNOs(){
        List<CardInfo> result = bindingService.selectCardNOs(idNo,idType);
        Assert.assertNotNull(result);
    }

    @Test
    public void testIsLocked(){
        boolean result = bindingService.isLocked(openId,idNo);
        Assert.assertEquals(false,result);
    }

    @Test
    public void testAddCountCache(){
        bindingService.addCountCache(openId,idNo);
    }
}
