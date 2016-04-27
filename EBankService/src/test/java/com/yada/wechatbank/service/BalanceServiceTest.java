package com.yada.wechatbank.service;

import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.impl.BalanceServiceImpl;
import com.yada.wechatbank.util.Crypt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 我的额度Service测试
 * Created by pangChangSong on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BalanceServiceTest {

    @Autowired
    private BalanceService balanceServiceImpl;

    /**
     * 根据卡号查询额度,有查询结果的情况
     */
    @Test
    public void TestGetCardNoBalance() {
        String cardNo = "11111111111111111";
        List<Balance> list = balanceServiceImpl.getCardNoBalance(cardNo);
        //断言两条数据
        Assert.assertEquals(2, list.size());
        //断言第一条数据的币种是人民币
        Assert.assertEquals("人民币", list.get(0).getCurrencyChinaCode());
        Assert.assertEquals("CNY", list.get(0).getCurrencyCode());
        //第二条数据的币种是美元
        Assert.assertEquals("美元", list.get(1).getCurrencyChinaCode());
        Assert.assertEquals("USD", list.get(1).getCurrencyCode());

    }

    /**
     * 根据卡号查询额度,无查询结果的情况
     */
    @Test
    public void testGetCardNoBalanceBack0() {
        String cardNo = "222222222222222222";
        List<Balance> list = balanceServiceImpl.getCardNoBalance(cardNo);
        Assert.assertEquals(0, list.size());
    }

    /**
     * 根据卡号查询额度,发生错误，返回Null的情况
     */
    @Test
    public void testGetCardNoBalanceBackNull() {
        String cardNo = "222222222222222223";
        List<Balance> list = balanceServiceImpl.getCardNoBalance(cardNo);
        Assert.assertNull(list);
    }

    /**
     * 查询卡列表，返回正常情况
     */
    @Test
    public void testGetProessCardNoList() {
        String idType = "03";
        String idNo = "MOCK01";
        List<CardInfo> list = balanceServiceImpl.getProessCardNoList(idType, idNo);
        for (int i = 0; i < list.size(); i++) {
            //得到加密后的卡号
            String cardNo = list.get(i).getCardNo();
            //获得解密后卡号
            try {
                String decodeCardNo = Crypt.decode(cardNo.substring(cardNo.indexOf(",") + 1));
                //断言加密后的卡号是
                Assert.assertEquals(cardNo.substring(0, cardNo.indexOf(",") ), decodeCardNo.substring(0, 4) + "********" +
                        decodeCardNo.substring(decodeCardNo.length() - 4, decodeCardNo.length()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 查询卡列表，返回正常情况
     */
    @Test
    public void testGetProessCardNoListBack0() {
        String idType = "04";
        String idNo = "MOCK02";
        List<CardInfo> list = balanceServiceImpl.getProessCardNoList(idType, idNo);
        Assert.assertNull(list);
    }
}
