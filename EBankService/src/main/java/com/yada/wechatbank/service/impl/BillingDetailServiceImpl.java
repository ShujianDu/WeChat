package com.yada.wechatbank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillingDetailResp;
import com.yada.wechatbank.model.BillingDetail;
import com.yada.wechatbank.service.BillingDetailService;
import com.yada.wechatbank.util.AmtUtil;
import com.yada.wechatbank.util.CurrencyUtil;

/**
 * 账单明细
 * 
 * @author liangtieluan
 *
 */
@Service
public class BillingDetailServiceImpl extends BaseService implements BillingDetailService {
	private final static Logger logger = LoggerFactory.getLogger(BillingDetailServiceImpl.class);
	// 已出账单明细查询
	@Value(value = "${url.alltBillingDetail}")
	protected String alltBillingDetailUrl;
	// 未出账单明细查询
	@Value(value = "${url.unsmBillingDetail}")
	protected String unsmBillingDetailUrl;

	@Override
	public List<BillingDetail> getBillingDetail(String cardNo, String queryType, String startnum, String totalnum, String periodStartDate,
			String periodEndDate, String currencyCode) {
		// 账单明细查询结果
		List<BillingDetail> billingDetailList = new ArrayList<>();
		BillingDetailResp billingDetailResp;
		Map<String, String> param = initGcsParam();
		param.put("cardNo", cardNo);
		param.put("queryType", queryType);
		param.put("startNum", startnum);
		param.put("totalNum", totalnum);
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
		if (billingDetailResp == null) {
			logger.error("@BillingDetail@billingDetailResp is null,cardNo[" + cardNo + "]");
			return null;
		} else if (billingDetailResp.getData() == null) {
			logger.info("@BillingDetail@billingDetailResp's data is null,cardNo[" + cardNo + "]");
			return billingDetailList;
		} else {
			billingDetailList = billingDetailResp.getData();
			for (BillingDetail billingDetail : billingDetailList) {
				// 设置中文显示币种
				billingDetail.setCurrencyChinaCode(CurrencyUtil.translateChinese(billingDetail.getCurrencyCode()));
				// 对金额字段进行处理
				billingDetail.setTransactionAmount(AmtUtil.procString(billingDetail.getTransactionAmount()));
			}
		}
		return billingDetailList;
	}
}
