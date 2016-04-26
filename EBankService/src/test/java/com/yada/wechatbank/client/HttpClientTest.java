package com.yada.wechatbank.client;

import com.yada.wechatbank.client.model.CardApplyResp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试HttpClient
 * Created by QinQiang on 2016/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class HttpClientTest {

    @Autowired
    private HttpClient httpClient;
    @Value("${url.cardApply}")
    private String reqUrl;
    private Map<String, String> param;

    @Before
    public void init() {
        param = new HashMap<>();
        param.put("key1", "value1");
        param.put("key2", "value2");
        param.put("key3", "value3");
    }

    @Test
    public void testSend() {
        CardApplyResp result = httpClient.send(reqUrl, param, CardApplyResp.class);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getBizResult());
        Assert.assertEquals("00", result.getReturnCode());
        Assert.assertEquals(true, result.getBizResult().getHasNext());
        Assert.assertEquals(10, result.getBizResult().getCardApplies().size());
    }
}
