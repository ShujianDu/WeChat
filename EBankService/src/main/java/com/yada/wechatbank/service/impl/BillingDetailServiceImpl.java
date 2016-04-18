package com.yada.wechatbank.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillingDetailResp;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.service.BillingDetailService;

@Service
public class BillingDetailServiceImpl extends BaseService implements BillingDetailService {
	private final static Logger logger = LoggerFactory.getLogger(BillingDetailServiceImpl.class);

	@Override
	public List<BillingDetail> getBillingDetail(String cardNo, String queryType, String STARTNUM, String TOTALNUM, String periodStartDate,
			String periodEndDate, String currencyCode) {
		BillingDetailResp billingDetailResp;
		Map<String, String> param = initGcsParam();
		param.put("cardNo", cardNo);
		param.put("queryType", queryType);
		param.put("startNum", STARTNUM);
		param.put("totalNum", TOTALNUM);
		// 已出账单查询
		if (queryType != null && "ALLT".equals(queryType)) {
			param.put("currencyCode", currencyCode);
			param.put("startDate", periodStartDate);
			param.put("endDate", periodEndDate);
			billingDetailResp = httpClient.send(alltBillingDetailUrl, param, BillingDetailResp.class);
		} else {
			// 未出账单查询
			billingDetailResp = httpClient.send(unsmBillingDetailUrl, param, BillingDetailResp.class);
		}
		if (billingDetailResp == null || billingDetailResp.getBizResult() == null) {
			logger.error("@BillingDetail@BillingDetail is null,cardNo[" + cardNo + "]");
			return null;
		}
		return billingDetailResp.getBizResult();
	}
}
