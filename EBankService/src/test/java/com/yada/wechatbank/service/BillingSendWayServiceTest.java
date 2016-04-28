package com.yada.wechatbank.service;

import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.model.BillSendType;
import org.junit.Assert;
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
    private String idNum;

    @Test
    public void testUpdateBillSendTypeNull(){
        String cardNo="6225888899990001";
        Boolean bool=billingSendWayService.updateBillSendType(cardNo,"C");
        Assert.assertTrue(bool);
    }

    @Test
    public void testGetBillSendTypeNotNull(){
        this.idType = "01";
        this.idNum = "MOCK01";
        List<BillSendType> billSendType = billingSendWayService.getBillSendType(idType, idNum);
        Assert.assertNotNull(billSendType);
    }

}
