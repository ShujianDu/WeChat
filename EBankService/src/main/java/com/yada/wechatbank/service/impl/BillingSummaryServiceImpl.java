package com.yada.wechatbank.service.impl;

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
import com.yada.wechatbank.service.BillingSummaryService;
import com.yada.wechatbank.util.Crypt;
import com.yada.wx.db.service.impl.CustomerInfoServiceImpl;

/**
 * 账单摘要业务层实现类
 * 
 * @author liangtieluan
 *
 */
@Service
public class BillingSummaryServiceImpl implements BillingSummaryService {
	private final static Logger logger = LoggerFactory.getLogger(BillingSummaryServiceImpl.class);
	/**
	 * 查询客户信息业务层
	 */
	@Autowired
	private CustomerInfoServiceImpl customerInfoServiceImpl;

	@Override
	public List<BillingSummary> getBillingSummaryList(List<String> queryCardList, String date) {
		// TODO 调用行内service查询账单摘要
		List<BillingSummary> billingSummaryList = null;
		return billingSummaryList;
	}

	@Override
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

	@Override
	public List<String> getEncryptCardNOs(List<String> cardList) {
		// 判断卡列表是否为空
		if (cardList == null) {
			return null;
		} else if (cardList.size() == 0) {
			return cardList;
		} else {
			// 不为空，加密卡列表
			try {
				return Crypt.cardNoCrypt(cardList);
			} catch (Exception e) {
				logger.error("@WDZD@cardList crypt error,cardList[" + cardList + "]:" + e);
				return null;
			}
		}
	}

	@Override
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
