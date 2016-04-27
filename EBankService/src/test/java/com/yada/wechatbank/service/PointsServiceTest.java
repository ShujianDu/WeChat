package com.yada.wechatbank.service;

import com.yada.wechatbank.model.PointsBalance;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.model.VerificationCardNoResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class PointsServiceTest {
    @Autowired
    private PointsService pointsService;

    private String idType="01";
    private String idNo="1234567891234";
    private String cardNo="123456";

    @Test
    public void testGetPointsBlance(){
        PointsBalance pointsBalance = pointsService.getPointsBlance(idNo,idType);
        Assert.assertNotNull(pointsBalance);
    }

    @Test
    public void testGetPointsDetail(){
        List<PointsDetail> result = pointsService.getPointsDetail(idNo,idType);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void testGetPointsValidates(){
        List<PointsValidates> result = pointsService.getPointsValidates(cardNo);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void testGetCardN0(){
        String result = pointsService.getCardN0(idNo,idType);
        Assert.assertNotNull(result);
    }

    @Test
    public void testVerificationCardNo(){
        VerificationCardNoResult result = pointsService.verificationCardNo(cardNo);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetList(){
        List<PointsDetail> list = new ArrayList<>();
        PointsDetail pointsDetail = new PointsDetail();
        pointsDetail.setId("11");
        pointsDetail.setPointuseFlg("qq");
        pointsDetail.setProductCode("1232");
        List<List<PointsDetail>> result = pointsService.getList(list);
        Assert.assertNotNull(result);
    }


}
