package com.yada.wechatbank.service;

import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;

import java.util.List;

/**
 * 我的额度服务接口
 * @author tx
 *
 */
public interface BalanceService {
	/**
	 * 为前台处理需要展示的额度信息
	 * @param cardNo  卡号
	 * @return 展示的用户额度列表
	 */
	List<Balance> getCardNoBalance(String cardNo ) ;

	/**
	 * 获取卡列表
	 * @param identityType   证件类型
	 * @param identityNo     证件号
	 * @return               返回处理后的卡列表信息
	 */
	List<CardInfo> getProessCardNoList(String identityType, String identityNo);
}
