package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardApplyList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 预约办卡进度查询Test
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class CardApplyServiceTest {
    @Autowired
    private CardApplyService cardApplyService;

    private String identityType = "01";
    private String identityNo = "888999101010";
    private String mobileNo = "18888888888";

    private String name = "秦始皇";

    @Test
    public void testGetCrdCardSchedule() {
        CardApplyList cardApplyList = cardApplyService.getCrdCardSchedule(name, identityType, identityNo, 1);
        Assert.assertNotNull(cardApplyList);
        Assert.assertEquals(true, cardApplyList.getHasNext());
        Assert.assertNotEquals(0, cardApplyList.getCardApplies().size());
    }

    @Test
    public void testSendCardApplySMS() {
        String result1 = cardApplyService.sendCardApplySMS(identityType, identityNo, mobileNo);
        Assert.assertEquals("true", result1);
        String result2 = cardApplyService.sendCardApplySMS(identityType, identityNo, "19999999999");
        Assert.assertEquals("wrongMobileNo", result2);
    }
}
