package com.yada.wechatbank.service;

import com.yada.wechatbank.model.BillSendType;
import java.util.List;

/**
 * 账单寄送方式查询及修改接口
 * @author tx
 */
public interface BillingSendWayService{

	/**
	 * 获取账单寄送方式
	 * @param identityType 证件类型
	 * @param identityNo   证件号
	 * @return 账单寄送方式集合
	 */
	List<BillSendType> getBillSendType(String identityType,String identityNo) ;

	/**
	 * 账单寄送方式修改
     * @param billSendType  账单寄送方式标识
	 * @param cardNo        卡号
	 * @return 是否成功
	 */
	boolean updateBillSendType(String cardNo,String billSendType);
	/**
	 * 卡号加密+卡号遮掩
     * @param billSendType  账单寄送方式实体
	 */
	void processShowCardNo(BillSendType billSendType) throws Exception;
}
