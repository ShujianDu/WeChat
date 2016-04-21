package com.yada.wechatbank.service.impl;

import java.util.List;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.service.ConsumptionInstallmentService;

/**
 * 消费分期业务实现类
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentServiceImpl extends BaseService implements ConsumptionInstallmentService {
	@Override
	public List<String> selectCardNoList(String identityType, String identityNo) {
		return super.selectCardNoList(identityType, identityNo);
	}
}
