package com.yada.wechatbank.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yada.wechatbank.model.BillingDetail;

/**
 * 账单明细测试类
 * 
 * @author liangtieluan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BillingDetailServiceImplTest {
	@Autowired
	private BillingDetailService billingDetailService;
	private String cardNo1;

	@Before
	public void init() {
		cardNo1 = "6225888899990001";
	}

	@Test
	public void testGetBillingDetail() {
		List<BillingDetail> billingDetailList;
		// 查询已出账单
		billingDetailList = billingDetailService.getBillingDetail(cardNo1, "ALLT", "1", "10", "2016-04-01", "2016-04-15", "CNY");
		Assert.assertTrue(billingDetailList.size() == 1);
		Assert.assertEquals("CNY", billingDetailList.get(0).getCurrencyCode());
		Assert.assertEquals("人民币", billingDetailList.get(0).getCurrencyChinaCode());
		// 查询未出账单
		billingDetailList = billingDetailService.getBillingDetail(cardNo1, "UNSM", "1", "10", "2016-04-01", "2016-04-15", "CNY");
		Assert.assertTrue(billingDetailList.size() == 2);
		Assert.assertEquals("USD", billingDetailList.get(0).getCurrencyCode());
		Assert.assertEquals("美元", billingDetailList.get(0).getCurrencyChinaCode());
		// 查询无结果，返回null
		billingDetailList = billingDetailService.getBillingDetail(cardNo1, "", "1", "10", "2016-04-01", "2016-04-15", "CNY");
		Assert.assertTrue(billingDetailList.size() == 0);
	}
}
