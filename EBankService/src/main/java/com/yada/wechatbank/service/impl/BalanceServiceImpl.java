package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 我的额度
 * @author tx
 */
@Service
public class BalanceServiceImpl extends BaseService implements BalanceService {
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());

	public List<List<Balance>> getList(String identityType,String identityNo ) {

		List<CardInfo> cardInfos=selectCardNos(identityType,identityNo);
		//TODO 与后台接口调用获取额度列表
		List<Balance> balanceList=null;

		logger.debug("@WDED@通过卡列表[{}]获取到的额度集合为[{}]", cardInfos,balanceList);
		//判断是否获取到额度数据
		if (balanceList == null) {
			return null;
		} else if (balanceList.size() == 0) {
			return new ArrayList<>();
		}

		List<List<Balance>> newList = new ArrayList<>();
		List<Balance> listFirst = new LinkedList<>();
		listFirst.add(balanceList.get(0));
		newList.add(listFirst);
		int flag=0;

		for (int i = 1; i < balanceList.size(); i++) {
			if (balanceList.get(i).getCurrencyCode()!=null && !"".equals(balanceList.get(i).getCurrencyCode())) {
				for (List<Balance> aNewList : newList) {
					if (balanceList.get(i).getCardNo()
							.equals(aNewList.get(0).getCardNo())) {
						flag = 1;
						// 人民币放在第一
						if ("人民币".equals(balanceList.get(i).getCurrencyCode())) {
							aNewList.add(0, balanceList.get(i));
						} else {
							aNewList.add(balanceList.get(i));
						}
						break;
					} else {
						flag = 0;
					}
				}
				if(flag==0){
					List<Balance> listEntity=new LinkedList<>();
					listEntity.add(balanceList.get(i));
					newList.add(listEntity);
				}
			}else{
				List<Balance> listEntity=new LinkedList<>();
				listEntity.add(balanceList.get(i));
				newList.add(listEntity);
			}
		}
		return newList;
	}
}
