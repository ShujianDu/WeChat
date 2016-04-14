package com.yada.wechatbank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.service.BillingDetailService;

@Service
public class BillingDetailServiceImpl extends BaseService implements BillingDetailService {

	@Override
	public List<BillingDetail> getBillingDetail(String cardNo, String queryType, String STARTNUM, String TOTALNUM, String periodStartDate,
			String periodEndDate, String currencyCode) {
		// TODO 组装json数据通过行内service查询账单明细
		return null;
	}
}
