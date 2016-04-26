package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.InstallmentInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分期流水Dao测试
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class InstallmentInfoDaoTest {

    @Autowired
    private InstallmentInfoDao installmentInfoDao;

    private InstallmentInfo installmentInfo1;
    private InstallmentInfo installmentInfo2;

    private String cardNo = "6225880148528412";

    @Before
    public void init() {
        installmentInfo1 = new InstallmentInfo();
        installmentInfo1.setCardNo(cardNo);
        installmentInfo2 = new InstallmentInfo();
        installmentInfo2.setCardNo(cardNo);

    }

    @Test
    public void testInsert() {
        installmentInfo1 = installmentInfoDao.saveAndFlush(installmentInfo1);
        installmentInfo2 = installmentInfoDao.saveAndFlush(installmentInfo2);

        InstallmentInfo result1 = installmentInfoDao.findOne(installmentInfo1.getId());
        InstallmentInfo result2 = installmentInfoDao.findOne(installmentInfo2.getId());

        Assert.assertEquals(cardNo, result1.getCardNo());
        Assert.assertEquals(cardNo, result2.getCardNo());
    }

}
