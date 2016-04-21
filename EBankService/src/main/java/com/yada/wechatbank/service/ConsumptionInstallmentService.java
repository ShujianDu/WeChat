package com.yada.wechatbank.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 消费分期业务接口
 * 
 * @author liangtieluan
 *
 */
@Service
public interface ConsumptionInstallmentService {
	/**
	 * 获取卡号列表
	 *
	 * @param identityType
	 *            证件类型
	 * @param identityNo
	 *            证件号
	 * @return List<String>
	 */
	List<String> selectCardNoList(String identityType, String identityNo);

	/**
	 * 查询所有可分期的消费交易
	 * 
	 * @param cardNo
	 *            卡号
	 * @param currencyCode
	 *            币种
	 * @param startNumber
	 *            开始条数
	 * @param selectNumber
	 *            查询条数
	 * @return 可以办理消费分期的信息
	 */
	Map<String, Object> queryConsumptionInstallments(String cardNo, String currencyCode, String startNumber, String selectNumber);
}
