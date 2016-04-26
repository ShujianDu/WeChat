package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.AllCustomerInfo;
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
 * 推送管理Dao测试
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class AllCustomerInfoDaoTest {

    @Autowired
    private AllCustomerInfoDao allCustomerInfoDao;

    private AllCustomerInfo allCustomerInfo;
    private String identityNo = "888999101010";

    @Before
    public void init() {
        allCustomerInfo = new AllCustomerInfo();
        allCustomerInfo.setCardNo("6225880148528412");
        allCustomerInfo.setIdentityNo(identityNo);
        allCustomerInfo.setNotice("0");
        allCustomerInfo.setBillNotice("0");
        allCustomerInfo.setRepaymentNotice("0");
        allCustomerInfo = allCustomerInfoDao.saveAndFlush(allCustomerInfo);
    }

    @Test
    public void testFindByIdentityNo() {
        List<AllCustomerInfo> infoList = allCustomerInfoDao.findByIdentityNo(identityNo);
        Assert.assertEquals(1, infoList.size());
    }

    @Test
    public void testUpdateNoticeByIdentityNo() {
        int num = allCustomerInfoDao.updateNoticeByIdentityNo("1", "1", "1", identityNo);
        Assert.assertEquals(1, num);
        AllCustomerInfo info = allCustomerInfoDao.findOne(allCustomerInfo.getId());
        Assert.assertEquals("1", info.getNotice());
        Assert.assertEquals("1", info.getBillNotice());
        Assert.assertEquals("1", info.getRepaymentNotice());
    }
}
