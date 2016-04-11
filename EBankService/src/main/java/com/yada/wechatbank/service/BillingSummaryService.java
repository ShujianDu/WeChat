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
	 * 查询账单摘要集合
	 * 
	 * @param queryCardList
	 *            查询摘要卡列表
	 * @param date
	 *            查询日期
	 * @return 账单摘要集合
	 */
	public List<BillingSummary> getBillingSummaryList(List<String> queryCardList, String date);

	/**
	 * 
	 * @param cardNo
	 *            单张卡号
	 * @param cardList
	 *            卡列表
	 * @return 需要查询账单摘要的卡集合
	 * @throws Exception
	 *             加密时肯能出错，抛异常
	 * 
	 */
	public List<String> getQueryCardList(String cardNo, List<String> cardList) throws Exception;

	/**
	 * 
	 * @param identityNo
	 *            证件号
	 * @return 卡列表集合
	 */
	public List<String> getEncryptCardNOs(List<String> cardList);

	/**
	 * 获得用户可选账单月份
	 * 
	 * @return 日期集合
	 */
	public List<String> getDateList();
}
