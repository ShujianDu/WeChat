package com.yada.wechatbank.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.ConsumerAuthorizationResultResp;
import com.yada.wechatbank.client.model.ConsumptionInstallmentCostResp;
import com.yada.wechatbank.client.model.ConsumptionInstallmentsResp;
import com.yada.wechatbank.model.ConsumptionInstallmentAuthorization;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;
import com.yada.wechatbank.model.ConsumptionInstallments;
import com.yada.wechatbank.model.ConsumptionInstallmentsesReceive;
import com.yada.wechatbank.service.ConsumptionInstallmentService;
import com.yada.wechatbank.util.CurrencyUtil;

/**
 * 消费分期业务实现类
 * 
 * @author liangtieluan
 *
 */
@Service
public class ConsumptionInstallmentServiceImpl extends BaseService implements ConsumptionInstallmentService {
	// 查询所有可分期的消费交易
	@Value(value = "${url.queryConsumptionInstallments}")
	protected String queryConsumptionInstallmentsUrl;
	// 从配置文件读取消费分期交易金额下限
	@Value("${consumptionInstallmentMinAmount}")
	private String consumptionInstallmentMinAmount;
	// 消费分期试算
	@Value("${url.costConsumptionInstallment}")
	private String costConsumptionInstallmentUrl;
	// 消费分期授权
	@Value("${url.authorizationConsumptionInstallment}")
	private String authorizationConsumptionInstallmentUrl;
	@Autowired
	private CurrencyUtil currencyUtil;

	@Override
	public List<String> selectCardNoList(String identityType, String identityNo) {
		return super.selectCardNoList(identityType, identityNo);
	}

	@Override
	public Map<String, Object> queryConsumptionInstallments(String cardNo, String currencyCode, String startNumber, String selectNumber) {
		ConsumptionInstallmentsesReceive consumptionInstallmentsesReceive;
		// 方法返回值
		Map<String, Object> map = new HashMap<>();
		// 返回值中消费信息集合
		List<ConsumptionInstallments> consumptionInstallmentsList = new ArrayList<>();
		Map<String, String> param = initGcsParam();
		param.put("cardNo", cardNo);
		param.put("currencyCode", currencyCode);
		param.put("startNumber", startNumber);
		param.put("selectNumber", selectNumber);
		ConsumptionInstallmentsResp consumptionInstallmentsResp = httpClient.send(queryConsumptionInstallmentsUrl, param, ConsumptionInstallmentsResp.class);
		// 判断查询信息是否为空
		if (consumptionInstallmentsResp == null || consumptionInstallmentsResp.getBizResult() == null) {
			return null;
		} else {
			consumptionInstallmentsesReceive = consumptionInstallmentsResp.getBizResult();
		}
		// 是否还有更多信息查询，0没有，1有
		map.put("isFollowUp", consumptionInstallmentsesReceive.isHasNext() ? "0" : "1");
		for (ConsumptionInstallments consumptionInstallments : consumptionInstallmentsesReceive.getConsumptionInstallmentsList()) {
			// 交易金额---过滤出大于600的数据 借方、贷方---过滤出DEBT表示借方（例如，消费）
			if (Double.parseDouble(consumptionInstallments.getTransactionAmount()) >= Double.parseDouble(consumptionInstallmentMinAmount)
					&& "DEBT".equalsIgnoreCase(consumptionInstallments.getDebitCreditCode())) {
				consumptionInstallments.setCurrencyChinaCode(currencyUtil.translateChinese(consumptionInstallments.getOriginalCurrencyCode()));
				consumptionInstallmentsList.add(consumptionInstallments);
			}
		}
		map.put("consumptionInstallmentsList", consumptionInstallmentsList);
		return map;
	}

	@Override
	public ConsumptionInstallmentCost costConsumptionInstallment(ConsumptionInstallmentAuthorization consumptionInstallmentAuthorization) {
		Map<String, String> param = initGcsParam();
		param.put("accountKeyOne", consumptionInstallmentAuthorization.getAccountKeyOne());
		param.put("accountKeyTwo", consumptionInstallmentAuthorization.getAccountKeyTwo());
		param.put("currencyCode", consumptionInstallmentAuthorization.getCurrencyCode());
		param.put("billDateNo", consumptionInstallmentAuthorization.getBillDateNo());
		param.put("transactionAmount", consumptionInstallmentAuthorization.getTransactionAmount());
		param.put("cardNo", consumptionInstallmentAuthorization.getCardNo());
		param.put("accountNoID", consumptionInstallmentAuthorization.getAccountNoID());
		param.put("installmentPeriods", consumptionInstallmentAuthorization.getInstallmentPeriods());
		param.put("isfeeFlag", consumptionInstallmentAuthorization.getIsfeeFlag());
		ConsumptionInstallmentCostResp consumptionInstallmentCostResp = httpClient.send(costConsumptionInstallmentUrl, param,
				ConsumptionInstallmentCostResp.class);
		if (consumptionInstallmentCostResp == null || consumptionInstallmentCostResp.getBizResult() == null) {
			return null;
		}
		return consumptionInstallmentCostResp.getBizResult();
	}

	@Override
	public String authorizationConsumptionInstallment(ConsumptionInstallmentAuthorization consumptionInstallmentAuthorization) {
		Map<String, String> param = initGcsParam();
		param.put("accountKeyOne", consumptionInstallmentAuthorization.getAccountKeyOne());
		param.put("accountKeyTwo", consumptionInstallmentAuthorization.getAccountKeyTwo());
		param.put("currencyCode", consumptionInstallmentAuthorization.getCurrencyCode());
		param.put("billDateNo", consumptionInstallmentAuthorization.getBillDateNo());
		param.put("transactionAmount", consumptionInstallmentAuthorization.getTransactionAmount());
		param.put("cardNo", consumptionInstallmentAuthorization.getCardNo());
		param.put("accountNoID", consumptionInstallmentAuthorization.getAccountNoID());
		param.put("installmentPeriods", consumptionInstallmentAuthorization.getInstallmentPeriods());
		param.put("isfeeFlag", consumptionInstallmentAuthorization.getIsfeeFlag());
		ConsumerAuthorizationResultResp consumerAuthorizationResultResp = httpClient.send(authorizationConsumptionInstallmentUrl, param,
				ConsumerAuthorizationResultResp.class);
		if (consumerAuthorizationResultResp == null || consumerAuthorizationResultResp.getBizResult() == null) {
			return null;
		}
		return consumerAuthorizationResultResp.getBizResult().getReturnCode();
	}

	@Override
	public String verificationMobileNo(String identityType, String identityNo, String mobileNo) {
		String mobile = getCustMobileNo(identityType, identityNo);
		if (mobile == null) {
			return "exception";
		} else if ("".equals(mobile.trim())) {
			return "noMobileNumber";
		} else if (!mobile.equals(mobileNo)) {
			return "wrongMobilNo";
		}
		return "";
	}
}
