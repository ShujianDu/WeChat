package com.yada.wechatbank.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillingPeriodResp;
import com.yada.wechatbank.client.model.BillingSummaryResp;
import com.yada.wechatbank.model.BillingPeriod;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.service.BillingSummaryService;
import com.yada.wechatbank.util.CurrencyUtil;
import com.yada.wechatbank.util.DateUtil;

/**
 * 账单摘要业务层实现类
 * 
 * @author liangtieluan
 *
 */
@Service
public class BillingSummaryServiceImpl extends BaseService implements BillingSummaryService {
	private final static Logger logger = LoggerFactory.getLogger(BillingSummaryServiceImpl.class);
	// 账期查询
	@Value(value = "${url.billingPeriod}")
	protected String billingPeriodUrl;
	// 账单摘要查询
	@Value(value = "${url.billingSummary}")
	protected String billingSummaryUrl;

	@Override
	public List<BillingSummary> getBillingSummaryList(String cardNo, String date) throws Exception {
		// 账单摘要集合
		List<BillingSummary> billingSummaries = new ArrayList<>();
		// 可用账期
		List<BillingPeriod> usableBillPeriods = new ArrayList<>();
		// 查询到的账期
		List<BillingPeriod> billingPeriods;
		// 账单摘要实体
		BillingSummary billingSummary;
		// 根据卡号获取查询账期需要数据
		Map<String, String> paramPeriods = initGcsParam();
		paramPeriods.put("cardNo", cardNo);
		BillingPeriodResp billingPeriodResp = httpClient.send(billingPeriodUrl, paramPeriods, BillingPeriodResp.class);
		// 判断账期
		if (billingPeriodResp == null) {
			logger.info("@BillingPeriods@BillingPeriods is null,cardNo[" + cardNo + "]");
			return null;
		} else if (billingPeriodResp.getBizResult() == null) {
			// 没有账期
			return billingSummaries;
		}
		// 账期不为空，判断有无合适账期
		billingPeriods = billingPeriodResp.getBizResult();
		for (BillingPeriod temp : billingPeriods) {
			String formatDate = DateUtil.parseDate(date, "yyyyMM", "yyyy-MM");
			if (temp.getPeriodEndDate().startsWith(formatDate)) {
				usableBillPeriods.add(temp);
			}
		}
		// 没有可用账期
		if (usableBillPeriods.size() == 0) {
			return billingSummaries;
		} else {
			// 有可用账期,循环账期查询账单摘要
			Map<String, String> paramBillingSummary = initGcsParam();
			for (BillingPeriod billingPeriod : usableBillPeriods) {
				paramBillingSummary.put("statementNo", billingPeriod.getStatementNo());
				paramBillingSummary.put("accountId", billingPeriod.getAccountId());
				BillingSummaryResp billingSummaryResp = httpClient.send(billingSummaryUrl, paramBillingSummary, BillingSummaryResp.class);
				if (billingSummaryResp == null) {
					logger.info("@BillingSummary@BillingSummary is null,cardNo[" + cardNo + "]billingPeriod[" + billingPeriod + "]");
					return null;
				} else if (billingSummaryResp.getBizResult() == null) {
					billingSummary = new BillingSummary();
					billingSummary.setClosingBalance("0");
					billingSummaries.add(billingSummary);
				} else {
					billingSummary = billingSummaryResp.getBizResult();
					// 设置中文显示币种
					billingSummary.setCurrencyChinaCode(CurrencyUtil.translateChinese(billingSummary.getCurrencyCode()));
				}
				billingSummaries.add(billingSummary);
			}
		}
		return billingSummaries;
	}

	@Override
	public List<String> getDateList() {
		List<String> list = new ArrayList<>();
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
			throw new RuntimeException("@WDZD@getDateList error,ParseException");
		}
		return list;
	}

	@Override
	public List<String> selectCardNoList(String identityType, String identityNo) {
		return super.selectCardNoList(identityType, identityNo);
	}
}
