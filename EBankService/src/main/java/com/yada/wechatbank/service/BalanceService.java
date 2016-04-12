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
	 * @param cardInfos 卡列表
	 * @return 展示的用户额度列表
	 */
	 List<List<Balance>> getList(List<CardInfo> cardInfos) ;
}