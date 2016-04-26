package com.yada.wechatbank.service;

import com.yada.wechatbank.permit.PermitHander;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 权限测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class PermitHanderTest {

    @Autowired
    private PermitHander permitHander;

    private String identityNo = "123456";
    private String password = "147852";
    private String identityType = "01";

    @Test
    public void testHasPermits(){
        boolean result =  permitHander.hasPermits(identityNo,password,identityType);
        Assert.assertEquals(true,result);
    }
}
