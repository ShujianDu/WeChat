package com.yada.wechatbank.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yada.wechatbank.model.ConsumptionInstallmentAuthorization;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;
import com.yada.wechatbank.model.ConsumptionInstallments;

/**
 * 消费分期测试
 * 
 * @author liangtieluan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class ConsumptionInstallmentServiceImplTest {
	@Autowired
	private ConsumptionInstallmentService consumptionInstallmentService;
	private ConsumptionInstallmentAuthorization c;
	private ConsumptionInstallmentAuthorization c1;

	@Before
	public void init() {
		c = new ConsumptionInstallmentAuthorization();
		c.setAccountKeyOne("01");
		c.setAccountKeyTwo("02");
		c.setCurrencyCode("CNY");
		c.setBillDateNo("4");
		c.setTransactionAmount("1111");
		c.setCardNo("1111111111111111");
		c.setAccountNoID("03");
		c.setInstallmentPeriods("6");
		c.setIsfeeFlag("1");
		c1 = new ConsumptionInstallmentAuthorization();
		c1.setAccountKeyOne("01");
		c1.setAccountKeyTwo("02");
		c1.setCurrencyCode("USD");
		c1.setBillDateNo("4");
		c1.setTransactionAmount("1111");
		c1.setCardNo("1111111111111111");
		c1.setAccountNoID("03");
		c1.setInstallmentPeriods("6");
		c1.setIsfeeFlag("1");
	}

	/**
	 * 测试可消费分期信息查询
	 */
	@Test
	public void testQueryConsumptionInstallments() {
		Map<String, Object> map;
		List<ConsumptionInstallments> consumptionInstallmentsList;
		map = consumptionInstallmentService.queryConsumptionInstallments("1111111111111111", "CNY", "1", "10");
		Assert.assertNotNull(map);
		consumptionInstallmentsList = (List<ConsumptionInstallments>) map.get("consumptionInstallmentsList");
		Assert.assertTrue(consumptionInstallmentsList.size() == 1);
		Assert.assertEquals("0", map.get("isFollowUp"));
		map = consumptionInstallmentService.queryConsumptionInstallments("1111111111111111", "USD", "1", "10");
		Assert.assertNotNull(map);
		consumptionInstallmentsList = (List<ConsumptionInstallments>) map.get("consumptionInstallmentsList");
		Assert.assertTrue(consumptionInstallmentsList.size() == 0);
		Assert.assertEquals("0", map.get("isFollowUp"));
	}

	/**
	 * 测试消费分期试算
	 */
	@Test
	public void testCostConsumptionInstallment() {
		ConsumptionInstallmentCost consumptionInstallmentCost;
		consumptionInstallmentCost = consumptionInstallmentService.costConsumptionInstallment(c);
		Assert.assertNotNull(consumptionInstallmentCost);
		Assert.assertEquals("CNY", consumptionInstallmentCost.getCurrencyCode());
		Assert.assertEquals("人民币", consumptionInstallmentCost.getCurrencyChinaCode());
		consumptionInstallmentCost = consumptionInstallmentService.costConsumptionInstallment(c1);
		Assert.assertNull(consumptionInstallmentCost);
	}

	/**
	 * 测试消费分期授权
	 */
	@Test
	public void testAuthorizationConsumptionInstallment() {
		boolean result;
		result = consumptionInstallmentService.authorizationConsumptionInstallment(c);
		Assert.assertTrue(result);
		result = consumptionInstallmentService.authorizationConsumptionInstallment(c1);
		Assert.assertFalse(result);
		c.setCurrencyCode("");
		result = consumptionInstallmentService.authorizationConsumptionInstallment(c);
		Assert.assertFalse(result);
	}

	/**
	 * 测试验证手机号
	 */
	@Test
	public void testVerificationMobileNo() {
		String result;
		result = consumptionInstallmentService.verificationMobileNo("03", "MOCK01", "1888888888");
		Assert.assertEquals("wrongMobilNo", result);
		result = consumptionInstallmentService.verificationMobileNo("03", "MOCK01", "18888888888");
		Assert.assertEquals("", result);
	}
}
