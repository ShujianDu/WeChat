package com.yada.wechatbank.service;

import java.util.List;

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
}
