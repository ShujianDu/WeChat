package com.yada.wechatbank.service;

import com.yada.wechatbank.model.BillSendType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Tx on 2016/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BillingSendWayServiceTest {

    @Autowired
    private BillingSendWayService billingSendWayService;

    private String idType;
    private String idNo;
    private String pwd;
    private String mobileNo;


    @Before
    public void init(){
        this.idType = "01";
        this.idNo = "130100199009112427";
    }

    @Test
    public void testgetBillSendType(){
        List<BillSendType> billSendType = billingSendWayService.getBillSendType(idType, idNo);
        Assert.assertNull(billSendType);
    }

}
