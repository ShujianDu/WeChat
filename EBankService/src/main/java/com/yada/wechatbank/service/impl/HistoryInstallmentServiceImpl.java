package com.yada.wechatbank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.HistoryInstallmentResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.model.HistoryInstallmentList;
import com.yada.wechatbank.service.HistoryInstallmentService;
import com.yada.wechatbank.util.AmtUtil;
import com.yada.wechatbank.util.CurrencyUtil;

/**
 * Created by Echo on 2016/4/12.
 */
@Service
public class HistoryInstallmentServiceImpl extends BaseService implements HistoryInstallmentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HttpClient httpClient;
	@Value("${url.getHistoryInstallment}")
	private String getHistoryInstallment;
	@Autowired
	private MessageProducer messageProducer;

	@Override
	public HistoryInstallmentList queryHistoryInstallment(String cardNo, String startNumber, String selectNumber) {
		// 调用后台查询分期历史
		Map<String, String> map = initGcsParam();
		map.put("cardNo", cardNo);
		map.put("startNumber", startNumber);
		map.put("selectNumber", selectNumber);
		HistoryInstallmentResp historyInstallmentResp = httpClient.send(getHistoryInstallment, map, HistoryInstallmentResp.class);
		if (historyInstallmentResp == null) {
			logger.warn("@FQLSCX@分期历史信息查询结果为空cardNo[{}]", cardNo);
			return null;
		}
		if (!"00".equals(historyInstallmentResp.getReturnCode())) {
			logger.warn("@FQLSCX@分期历史信息查询返回错误,错误码returnCode[{}]卡号cardNo[{}]", historyInstallmentResp.getReturnCode(), cardNo);
			return null;
		}
		HistoryInstallmentList historyInstallmentList = historyInstallmentResp.getData();
		List<HistoryInstallment> setList = new ArrayList<>();
		if (historyInstallmentList.getEntityList() == null || historyInstallmentList.getEntityList().size() == 0) {
			logger.warn("@FQLSCX@分期历史信息查询-信息列表为空或列表长度为0,cardNo[{}]", cardNo);
			return null;
		}
		// 替换币种
		for (HistoryInstallment historyInstallment : historyInstallmentList.getEntityList()) {
			historyInstallment.setCurrencyCode(CurrencyUtil.translateChinese(historyInstallment.getCurrencyCode()));
			historyInstallment.setInstalmentOriginalAmount(AmtUtil.procString(historyInstallment.getInstalmentOriginalAmount()));
			historyInstallment.setInstalmentFirstPostingAmount(AmtUtil.procString(historyInstallment.getInstalmentFirstPostingAmount()));
			historyInstallment.setInstalmentNextPostingAmount(AmtUtil.procString(historyInstallment.getInstalmentNextPostingAmount()));
			historyInstallment.setInstalmentOutstandingAmount(AmtUtil.procString(historyInstallment.getInstalmentOutstandingAmount()));
			historyInstallment.setInstalmentReversalAmount(AmtUtil.procString(historyInstallment.getInstalmentReversalAmount()));
			setList.add(historyInstallment);
		}
		historyInstallmentList.setEntityList(setList);
		messageProducer.send(TopicEnum.EBANK_QUERY, "queryHistoryInstallment", map);
		return historyInstallmentList;
	}

	@Override
	public List<CardInfo> selectCardNOs(String identityType, String identityNo) {
		return selectCardNos(identityType, identityNo);
	}

}
