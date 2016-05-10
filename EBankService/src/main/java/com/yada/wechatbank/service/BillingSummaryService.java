package com.yada.wechatbank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yada.wechatbank.model.BillingSummary;

/**
 * 账单摘要业务层接口
 * 
 * @author liangtieluan
 *
 */
@Service
public interface BillingSummaryService {
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
	 * 查询账单摘要集合
	 * 
	 * @param cardNo
	 *            卡号
	 * @param date
	 *            账单查询日期
	 * @return 账单摘要
	 */
	List<BillingSummary> getBillingSummaryList(String cardNo, String date);

	/**
	 * 获得用户可选账单月份
	 * 
	 * @return 日期集合
	 */
	List<String> getDateList();
}
