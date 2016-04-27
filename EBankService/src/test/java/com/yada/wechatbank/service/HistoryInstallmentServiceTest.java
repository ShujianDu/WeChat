package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.HistoryInstallmentList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 分期历史查询测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class HistoryInstallmentServiceTest {

    @Autowired
    private HistoryInstallmentService historyInstallmentService;

    private String cardNo = "1234567891234";
    private String startNumber = "1";
    private String selectNumber = "10";
    private String idNo = "1234567891234";
    private String idType = "01";

    @Test
    public void testQueryHistoryInstallment(){
        HistoryInstallmentList result = historyInstallmentService.queryHistoryInstallment(cardNo,startNumber,selectNumber);
        Assert.assertNotNull(result);
    }

    @Test
    public void testSelectCardNOs(){
        List<CardInfo> result =  historyInstallmentService.selectCardNOs(idNo,idType);
        Assert.assertNotNull(result);
    }
}
