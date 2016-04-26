package com.yada.wx.db.service.dao;

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

/**
 * 客户信息Dao测试
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class CustomerInfoDaoTest {
    @Autowired
    private CustomerInfoDao customerInfoDao;

    private CustomerInfo customerInfo;

    private String openId = "oWnN_jnEvOb_fcViJ0YqeLwZQIZ2";
    private String identityNo = "888999101010";
    private String identityType = "01";

    @Before
    public void init() {
        customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityType(identityType);
        customerInfo.setBindingDate("20160426");
        customerInfo.setDefCardNo("6225880148528412");
        customerInfo.setIdentityNo(identityNo);
        customerInfo.setMobilePhone("18888888888");

        customerInfo = customerInfoDao.saveAndFlush(customerInfo);
    }

    @Test
    public void testFindByOpenId() {
        CustomerInfo result = customerInfoDao.findByOpenId(openId);
        Assert.assertNotNull(result);
        Assert.assertEquals(identityNo, result.getIdentityNo());
    }

    @Test
    public void testFindByIdentityTypeAndIdentityNo() {
        List<CustomerInfo> customerInfoList = customerInfoDao.findByIdentityTypeAndIdentityNo(identityType, identityNo);
        Assert.assertNotNull(customerInfoList);
        Assert.assertEquals(1, customerInfoList.size());
        Assert.assertEquals(openId, customerInfoList.get(0).getOpenId());
    }

    @Test
    public void testUpdateIdentityTypeByIdentityNo(){
        int num = customerInfoDao.updateIdentityTypeByIdentityNo("02", identityNo);
        Assert.assertEquals(1, num);
        CustomerInfo result = customerInfoDao.findByOpenId(openId);
        Assert.assertNotNull(result);
        Assert.assertEquals("02", result.getIdentityType());
    }

    @Test
    public void testGetOpenIdByidentityNo(){
        List<String> list = customerInfoDao.getOpenIdByidentityNo(identityNo);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(openId, list.get(0));
    }

    @Test
    public void testDeleteByOpenId(){
        int num = customerInfoDao.deleteByOpenId(openId);
        Assert.assertEquals(1, num);
        CustomerInfo result = customerInfoDao.findByOpenId(openId);
        Assert.assertNull(result);
    }
}
