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
	 * @param cardNo
	 *            卡号
	 * @param date
	 *            账单查询日期
	 * @return 账单摘要
	 * @throws Exception
	 *             卡号加密抛异常
	 */
	public List<BillingSummary> getBillingSummaryList(String cardNo, String date) throws Exception;

	/**
	 * 
	 * @param cardNo
	 *            单张卡号
	 * @param cardList
	 *            卡列表
	 * @return 需要查询账单摘要的卡集合
	 * @throws Exception
	 *             加密时肯能出错，抛出异常
	 * 
	 */
	List<String> getQueryCardList(String cardNo, List<String> cardList) throws Exception;

	/**
	 * 解密卡列表集合
	 * 
	 * @param cardList
	 *            需要加密的卡列表集合
	 * @return 加密好的卡列表
	 */
	List<String> getEncryptCardNOs(List<String> cardList);

	/**
	 * 获得用户可选账单月份
	 * 
	 * @return 日期集合
	 */
	List<String> getDateList();
}
