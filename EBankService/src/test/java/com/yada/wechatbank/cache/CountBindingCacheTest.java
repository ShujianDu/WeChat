package com.yada.wechatbank.cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by QinQiang on 2016/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class CountBindingCacheTest {
    @Autowired
    private ICountBindingCache countBindingCache;

    private String countKey1 = "CountBindingCacheTest1";
    private String countKey2 = "CountBindingCacheTest2";
    private String countKey3 = "CountBindingCacheTest3";
    private String identyNo1 = "111111111";
    private String identyNo2 = "222222222";
    private String identyNo3 = "333333333";

    @Test
    public void testPut(){
        String result = countBindingCache.put(countKey1, identyNo1);
        Assert.assertEquals("1", result);
    }

    @Test
    public void testPutAndGet(){
        countBindingCache.put(countKey2, identyNo1);

        String [] result1 = countBindingCache.get(countKey2);
        Assert.assertEquals("1", result1[0]);
        Assert.assertEquals(identyNo1, result1[1]);

        countBindingCache.put(countKey2, identyNo2);
        String [] result2 = countBindingCache.get(countKey2);
        Assert.assertEquals("2", result2[0]);
        Assert.assertEquals(identyNo1, result2[1]);
        Assert.assertEquals(identyNo2, result2[2]);

        countBindingCache.put(countKey2, identyNo3);
        String [] result3 = countBindingCache.get(countKey2);
        Assert.assertEquals("3", result3[0]);
        Assert.assertEquals(identyNo1, result3[1]);
        Assert.assertEquals(identyNo2, result2[2]);
        Assert.assertEquals(identyNo3, result2[3]);
    }

    @Test
    public void testRemove(){
        countBindingCache.put(countKey3, identyNo1);
        countBindingCache.remove(countKey3);
        String[] result = countBindingCache.get(countKey3);
        Assert.assertNull(result);
    }

}
