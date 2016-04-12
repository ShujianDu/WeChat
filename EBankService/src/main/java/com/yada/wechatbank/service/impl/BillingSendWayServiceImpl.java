package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.service.BillingSendWayService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingSendWayServiceImpl implements BillingSendWayService{
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());
	@Override
	public List<BillSendType> getBillSendType(String identityType,String identityNo) {

		//TODO 调取后台获取卡列表
		List<String> cardNos=null;

		List<BillSendType> list=new ArrayList<>();

		//TODO 增加判断后台是否业务错误返回空情况

		//TODO 获取卡对应的账单寄送方式地址
		for(String cardNo:cardNos)
		{

			//list.add();
		}

		logger.debug("@ZDJSFSCX@通过卡列表cardNos[{}]获取账单寄送方式集合为[{}]",cardNos,list);

		//如果有数据，则将卡号展示的部分隐藏
		if(list.size()!=0)
		{
			processShowCardNo(list);
		}
		return list;
	}

	@Override
	public boolean updateBillSendType(String cardNo,String billSendType) {
		//TODO 通过卡号和寄送方式到行内服务调取GCS修改
		return true;
	}








	/**
	 * 将卡号信息加密，并处理卡号遮盖
	 * @param list 账单寄送方式列表
	 */
  public void processShowCardNo(List<BillSendType> list)
  {
	  for (BillSendType billSendType : list) {
		  try {
			  String cardNo = billSendType.getCardNo();
			  if (cardNo != null && !"".equals(cardNo)) {
				  billSendType.setCardNo(
						  cardNo.substring(0, 4)
								  + "********"
								  + cardNo.substring(cardNo.length() - 4,
								  cardNo.length()) + ","
								  + Crypt.encode(cardNo));
			  }
		  } catch (Exception e) {
			  logger.error("@WDZD@卡号加密过程中出现错误cardNo["
					  + billSendType.getCardNo() + "]:", e);
		  }
	  }
  }

}