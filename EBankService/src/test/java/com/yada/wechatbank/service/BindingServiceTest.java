package com.yada.wechatbank.service;

import com.yada.wechatbank.cache.ICountSMSCache;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 绑定测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class BindingServiceTest {

    @Autowired
    private BindingService bindingService;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private ICountSMSCache countSMSCache;

    private String openId;
    private String idType;
    private String idNo;
    private String pwd;
    private String mobileNo;


    @Before
    public void init() {
        this.openId = "123456";
        this.idType = "SSNO";
        this.idNo = "MOCK01";
        this.pwd = "111111";
        this.mobileNo = "18888888888";
    }

    @Test
    public void testValidateIsBinding() {
        boolean result = bindingService.validateIsBinding(openId);
        Assert.assertEquals(false, result);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId("1212");
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfoDao.save(customerInfo);
        result = bindingService.validateIsBinding("1212");
        Assert.assertEquals(true, result);
    }


    @Test
    public void testCustBinding() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId("123456");
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfoDao.save(customerInfo);
        String result = bindingService.custBinding(openId, idType, "111", pwd);
        Assert.assertEquals("1", result);
        customerInfoDao.delete(customerInfo);
        CustomerInfo customerInfo1 = new CustomerInfo();
        customerInfo1.setOpenId("123456");
        customerInfo1.setIdentityNo(idNo);
        customerInfo1.setIdentityType(idType);
        customerInfoDao.save(customerInfo1);
        result = bindingService.custBinding(openId, idType, idNo, pwd);
        Assert.assertEquals("0", result);
    }


    @Test
    public void testIsExistIdType() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId("111");
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfoDao.save(customerInfo);
        Map<String, String> result = bindingService.isExistIdType("111");
        String res = result.get("isexist");
        Assert.assertEquals("true", res);
    }

    @Test
    public void testGetDefCardNo() {
        String result = bindingService.getDefCardNo("222");
        Assert.assertNull(result);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfo.setDefCardNo("651234567895");
        customerInfoDao.save(customerInfo);
        result = bindingService.getDefCardNo(openId);
        Assert.assertNotNull(result);
    }


    @Test
    public void testDefCardBinding() {
        boolean result = bindingService.defCardBinding(openId, idNo);
        Assert.assertEquals(false, result);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfo.setDefCardNo("651234567895");
        customerInfoDao.save(customerInfo);
        result = bindingService.defCardBinding(openId, idNo);
        Assert.assertEquals(true, result);
    }


    @Test
    public void testVaidateMobilNo() {
        String result = bindingService.vaidateMobilNo(openId, idNo, idType, mobileNo);
        Assert.assertEquals("true", result);
        for (int i = 0; i < 5; i++) {
            bindingService.addCountCache(openId, idNo);
        }
        result = bindingService.vaidateMobilNo(openId, idNo, idType, mobileNo);
        Assert.assertEquals("locked", result);
    }


    @Test
    public void testIsCorrectIdentityType() {
        boolean result = bindingService.isCorrectIdentityType(idNo, idType);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testFillIdentityType() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfoDao.save(customerInfo);
        boolean result = bindingService.fillIdentityType(idType, idNo);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSelectCardNOs() {
        List<CardInfo> result = bindingService.selectCardNOs(idType, idNo);
        Assert.assertNotNull(result);
    }

    @Test
    public void testIsLocked() {
        for (int i = 0; i < 5; i++) {
            bindingService.addCountCache(openId, idNo);
        }
        boolean result = bindingService.isLocked(openId, idNo);
        Assert.assertEquals(true, result);
        countSMSCache.remove(openId);
    }




    @Test
    public void testFindCustomerInfoByOpenId() {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityNo(idNo);
        customerInfo.setIdentityType(idType);
        customerInfoDao.save(customerInfo);
        CustomerInfo result = bindingService.findCustomerInfoByOpenId(openId);
        Assert.assertNotNull(result);
    }
}
