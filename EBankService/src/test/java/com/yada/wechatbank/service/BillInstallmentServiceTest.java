package com.yada.wechatbank.service;

import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.BillCost;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardInfo;
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
public class BillInstallmentServiceTest {

    @Autowired
    BillInstallmentService billInstallmentService;


    @Test
    public void testGetCurrentPeriodBill() {
        String cardNo = "6225888899990001";
        BillingSummary billingSummary = billInstallmentService.getCurrentPeriodBill(cardNo);
        Assert.assertNotNull(billingSummary);
    }

    @Test
    public void testGetAmountLimit() {
        String cardNo = "6225888899990001";
        String currencyCode = "CNY";
        AmountLimit amountLimit = billInstallmentService.getAmountLimit(cardNo, currencyCode);
        Assert.assertNotNull(amountLimit);
    }

    @Test
    public void testGetBillCost() {
        String accountId = "MOCK01AD";
        String accountNumber = "MOCK01AN";
        String currencyCode = "CNY";
        String billLowerAmount = "1000";
        String billActualAmount = "10000";
        String installmentsNumber = "3";
        String feeInstallmentsFlag = "1";
        BillCost billCost = billInstallmentService.getBillCost(accountId, accountNumber, currencyCode, billLowerAmount, billActualAmount, installmentsNumber, feeInstallmentsFlag);
        Assert.assertNotNull(billCost);
    }
    @Test
    public void testBillInstallment() {
        String accountId = "MOCK01AD";
        String accountNumber = "MOCK01AN";
        String currencyCode = "CNY";
        String billLowerAmount = "1000";
        String billActualAmount = "10000";
        String installmentsNumber = "3";
        String feeInstallmentsFlag = "1";
        String cardNo = "6225888899990001";
        Boolean bool = billInstallmentService.billInstallment(accountId, accountNumber, cardNo, currencyCode, billLowerAmount, billActualAmount, installmentsNumber, feeInstallmentsFlag);
        Assert.assertTrue(bool);
    }
    @Test
    public void testVerificationMobileNo() {
        String idType = "01";
        String idNum = "MOCK01";
        String mobileNo="18888888888";
        String result = billInstallmentService.verificationMobileNo(idType, idNum, mobileNo);
        Assert.assertEquals("", result);
    }

    /**
     * 查询卡列表，返回正常情况
     */
    @Test
    public void testGetProessCardNoList() {
        String idType = "03";
        String idNo = "MOCK01";
        List<CardInfo> list = billInstallmentService.getProessCardNoList(idType, idNo);
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

}
