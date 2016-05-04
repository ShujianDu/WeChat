package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardHolderInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Tx on 2016/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class CardHolderInfoServiceTest {

    @Autowired
    private CardHolderInfoService cardHolderInfoService;



    @Test
    public void testGetBillSendTypeNull(){
        String idType = "SSNO";
        String idNum = "MOCK01";
        CardHolderInfo cardHolder=cardHolderInfoService.getCardHolderInfo(idType,idNum);
        Assert.assertNotNull(cardHolder);
    }
}
