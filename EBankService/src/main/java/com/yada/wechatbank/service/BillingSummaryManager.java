package com.yada.wechatbank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.util.Crypt;
import com.yada.wx.db.service.impl.CustomerInfoServiceImpl;
import com.yada.wx.db.service.model.CustomerInfo;

/**
 * 账单摘要业务层
 * 
 * @author liangtieluan
 *
 */
@Service
public class BillingSummaryManager {
	private final static Logger logger = LoggerFactory.getLogger(BillingSummaryManager.class);
	/**
	 * 查询客户信息业务层
	 */
	@Autowired
	private CustomerInfoServiceImpl customerInfoServiceImpl;

	/**
	 * 查询账单摘要集合
	 * 
	 * @param queryCardList
	 *            查询摘要卡列表
	 * @param date
	 *            查询日期
	 * @return 账单摘要集合
	 */
	public List<BillingSummary> getBillingSummaryList(List<String> queryCardList, String date) {
		// TODO 调用行内service查询账单摘要
		List<BillingSummary> billingSummaryList = null;
		return billingSummaryList;
	}

	/**
	 * 
	 * @param cardNo
	 *            单张卡号
	 * @param cardList
	 *            卡列表
	 * @return 需要查询账单摘要的卡集合
	 * @throws Exception
	 *             当解密卡号的时候出错会抛出异常
	 */
	public List<String> getQueryCardList(String cardNo, List<String> cardList) throws Exception {
		List<String> queryCardList = new ArrayList<String>();
		if (cardNo == null || "".equals(cardNo) || cardList == null || cardList.size() == 0) {
			return null;
		}
		// 查询单张卡的账单摘要
		if (!"all".equals(cardNo)) {
			cardNo = Crypt.decode(cardNo);
			queryCardList.add(cardNo);
		} else {
			// 查询所有卡的账单摘要
			queryCardList = Crypt.cardNoDecode(cardList);
		}
		return queryCardList;
	}

	/**
	 * 
	 * @param identityNo
	 *            证件号
	 * @return 卡列表集合
	 */
	@SuppressWarnings("unused")
	public List<String> getCardNOs(String identityNo) {
		// 通过证件号获取用户信息列表，得到证件类型
		List<CustomerInfo> customerInfoList = customerInfoServiceImpl.getCustInfoByIdentityNo(identityNo);
		// 未查询到用户信息，返回null
		if (customerInfoList == null || customerInfoList.size() == 0) {
			return null;
		}
		String identityType = customerInfoList.get(0).getIdentityType();
		// TODO 连接行内service查询卡列表,需要用证件类型，证件号,完成后将@SuppressWarnings("unused")去掉
		List<String> cardList = null;
		if (cardList == null) {
			return null;
		} else if (cardList.size() == 0) {
			return cardList;
		} else {
			try {
				return Crypt.cardNoCrypt(cardList);
			} catch (Exception e) {
				logger.error("@WDZD@cardList crypt error,cardList[" + cardList + "]:" + e);
				return null;
			}
		}
	}

	/**
	 * 获得用户可选账单月份
	 * 
	 * @return 日期集合
	 */
	public List<String> getDateList() {
		List<String> list = new ArrayList<String>();
		try {
			for (int i = 0; i < 12; i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(sdf.format(cal.getTime())));
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - i);
				String dateStrY = new SimpleDateFormat("yyyyMM").format(cal.getTime());
				list.add(dateStrY);
			}
		} catch (ParseException e) {
			throw new RuntimeException("@BillSummary@getDateList error,ParseException");
		}
		return list;
	}
}
