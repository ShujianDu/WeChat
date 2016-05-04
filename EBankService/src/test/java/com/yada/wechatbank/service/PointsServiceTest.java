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

    private String idType="SSNO";
    private String idNo="MOCK01";
    private String cardNo="123456";

    @Test
    public void testGetPointsBlance(){
        PointsBalance pointsBalance = pointsService.getPointsBlance(idType ,idNo);
        Assert.assertNotNull(pointsBalance);
    }

    @Test
    public void testGetPointsDetail(){
        List<PointsDetail> result = pointsService.getPointsDetail(idType,idNo);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void testGetPointsValidates(){
        List<PointsValidates> result = pointsService.getPointsValidates(cardNo);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void testGetCardN0(){
        String result = pointsService.getCardNo(idType,idNo);
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
        pointsDetail.setTotalPoint("111");
        list.add(pointsDetail);
        PointsDetail pointsDetail1 = new PointsDetail();
        pointsDetail1.setId("22");
        pointsDetail1.setParentId("11");
        pointsDetail1.setPointuseFlg("正常");
        pointsDetail1.setProductCode("1232");
        pointsDetail1.setTotalPoint("222");
        list.add(pointsDetail1);
        PointsDetail pointsDetail2 = new PointsDetail();
        pointsDetail2.setId("33");
        pointsDetail2.setParentId("11");
        pointsDetail2.setPointuseFlg("冻结");
        pointsDetail2.setProductCode("1232");
        pointsDetail2.setTotalPoint("333");
        list.add(pointsDetail2);
        List<List<PointsDetail>> result = pointsService.getList(list);
        Assert.assertNotNull(result);
    }


}
