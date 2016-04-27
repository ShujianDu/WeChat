package com.yada.wechatbank.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yada.wechatbank.model.BillingSummary;

/**
 * 账单摘要业务测试类
 * 
 * @author liangtieluan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BillingSummaryServiceImplTest {
	@Autowired
	private BillingSummaryService billingSummaryService;
	String cardNo1;
	String cardNo2;

	@Before
	public void init() {
		cardNo1 = "6225888899990001";
		cardNo2 = "6225888899990002";
	}

	/**
	 * 测试账单摘要查询
	 */
	@Test
	public void testGetBillingSummaryList() {
		List<BillingSummary> billingSummaryList;
		// 卡号有账期，且月份符合
		try {
			billingSummaryList = billingSummaryService.getBillingSummaryList(cardNo1, "201604");
			Assert.assertTrue(billingSummaryList.size() == 2);
			Assert.assertEquals("CNY", billingSummaryList.get(0).getCurrencyCode());
			Assert.assertEquals("人民币", billingSummaryList.get(0).getCurrencyChinaCode());
			// 卡号不符合，月份符合
			billingSummaryList = billingSummaryService.getBillingSummaryList(cardNo2, "201605");
			Assert.assertTrue(billingSummaryList.size() == 0);
			// 卡号有账期，月份不符合
			billingSummaryList = billingSummaryService.getBillingSummaryList(cardNo1, "201605");
			Assert.assertTrue(billingSummaryList.size() == 0);
			// 卡号无账期
			billingSummaryList = billingSummaryService.getBillingSummaryList("1111111111111112", "201605");
			Assert.assertTrue(billingSummaryList.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试获取日期方法
	 */
	@Test
	public void testGetDateList() {
		List<String> dateList = billingSummaryService.getDateList();
		Assert.assertTrue(dateList.size() == 12);
	}
}
