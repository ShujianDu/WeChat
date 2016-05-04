package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.CreditLimitTemporaryUpReview;
import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;
import com.yada.wechatbank.util.Crypt;
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
public class CreditLimitTemporaryUpServiceTest {

    @Autowired
    CreditLimitTemporaryUpService creditLimitTemporaryUpService;



    @Test
    public void testTemporaryUpCommit() {
        String certType="03";
        String certNum="MOCK01";
        String cardNo="6225888899990001";
        String eosPreAddLimit="1000";
        String eosStarLimitDate="20160428";
        String eosEndLimitDate="20160909";
        String cardStyle="1";
        String issuingBranchId="0003";
        String pmtCreditLimit="10000";
        Boolean bool= creditLimitTemporaryUpService.temporaryUpCommit(certType, certNum, cardNo, eosPreAddLimit, eosStarLimitDate, eosEndLimitDate, cardStyle, issuingBranchId, pmtCreditLimit);
        Assert.assertTrue(bool);
    }

    @Test
    public void testGetLimitUpHistory() {
        String cardNo="6225888899990001";
        List<CreditLimitTemporaryUpStatus> list= creditLimitTemporaryUpService.getLimitUpHistory(cardNo);
        Assert.assertNotNull(list);
    }

    @Test
    public void testGetAmount() {
        String certType="03";
        String certNum="MOCK01";
        String cardNo="6225888899990001";
        CreditLimitTemporaryUpReview creditLimitTemporaryUpReview=creditLimitTemporaryUpService.getAmount(certType, certNum, cardNo);
        Assert.assertNotNull(creditLimitTemporaryUpReview);
    }

    /**
     * 查询卡列表，返回正常情况
     */
    @Test
    public void testGetProessCardNoList() {
        String idType = "SSNO";
        String idNo = "MOCK01";
        List<String> list = creditLimitTemporaryUpService.getProessCardNoList(idType, idNo);
        for (int i = 0; i < list.size(); i++) {
            //得到加密后的卡号
            String cardNo = list.get(i);
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

}
