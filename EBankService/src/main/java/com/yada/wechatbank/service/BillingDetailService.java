package com.yada.wechatbank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yada.wechatbank.model.BillingDetail;

@Service
public interface BillingDetailService {
	/**
	 * 获取账单明细集合
	 * 
	 * @param cardNo
	 *            卡号
	 * @param queryType
	 *            账单类型
	 * @param STARTNUM
	 *            查询开始条数
	 * @param TOTALNUM
	 *            查询总条数
	 * @param periodStartDate
	 *            账单开始日期
	 * @param periodEndDate
	 *            账单结束日期
	 * @param currencyCode
	 *            币种
	 * @return 账单明细集合
	 */
	public List<BillingDetail> getBillingDetail(String cardNo, String queryType, String STARTNUM, String TOTALNUM, String periodStartDate,
			String periodEndDate, String currencyCode);
}
