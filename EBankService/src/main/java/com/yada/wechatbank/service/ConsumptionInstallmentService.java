package com.yada.wechatbank.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yada.wechatbank.model.ConsumptionInstallmentAuthorization;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;

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

	/**
	 * 消费分期试算查询
	 * 
	 * @param consumptionInstallmentAuthorization
	 *            消费分期（费用试算）(TS011009)及消费分期授权(TS011011)上送报文实体
	 * @return 试算结果
	 */
	ConsumptionInstallmentCost costConsumptionInstallment(ConsumptionInstallmentAuthorization consumptionInstallmentAuthorization);

	/**
	 * 消费分期授权
	 * 
	 * @param consumptionInstallmentAuthorization
	 *            消费分期（费用试算）(TS011009)及消费分期授权(TS011011)上送报文实体
	 * @return 分期授权结果
	 */
	boolean authorizationConsumptionInstallment(ConsumptionInstallmentAuthorization consumptionInstallmentAuthorization);

	/**
	 * 验证手机号是否为用户注册的手机号
	 * 
	 * @param identityType
	 *            证件类型
	 * @param identityNo
	 *            证件号
	 * @param mobileNo
	 *            手机号
	 * @return 正确返回双引/错误返回信息
	 */
	String verificationMobileNo(String identityType, String identityNo, String mobileNo);
}
