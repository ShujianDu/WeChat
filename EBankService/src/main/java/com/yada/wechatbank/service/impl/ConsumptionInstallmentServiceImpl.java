package com.yada.wechatbank.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.ConsumerAuthorizationResultResp;
import com.yada.wechatbank.client.model.ConsumptionInstallmentCostResp;
import com.yada.wechatbank.client.model.ConsumptionInstallmentsResp;
import com.yada.wechatbank.model.ConsumptionInstallmentAuthorization;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;
import com.yada.wechatbank.model.ConsumptionInstallments;
import com.yada.wechatbank.model.ConsumptionInstallmentsesReceive;
import com.yada.wechatbank.service.ConsumptionInstallmentService;
import com.yada.wechatbank.util.AmtUtil;
import com.yada.wechatbank.util.CurrencyUtil;
import com.yada.wx.db.service.dao.InstallmentInfoDao;
import com.yada.wx.db.service.model.InstallmentInfo;

/**
 * 消费分期业务实现类
 * 
 * @author liangtieluan
 *
 */
@Service
@Transactional
public class ConsumptionInstallmentServiceImpl extends BaseService implements ConsumptionInstallmentService {
	private final Logger logger = LoggerFactory.getLogger(ConsumptionInstallmentServiceImpl.class);
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
	private InstallmentInfoDao installmentInfoDao;

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
		if (consumptionInstallmentsResp == null) {
			return null;
		} else if (consumptionInstallmentsResp.getData() == null) {
			map.put("consumptionInstallmentsList", consumptionInstallmentsList);
			map.put("isFollowUp", "0");
			return map;
		} else {
			consumptionInstallmentsesReceive = consumptionInstallmentsResp.getData();
		}
		// 是否还有更多信息查询，0没有，1有
		map.put("isFollowUp", consumptionInstallmentsesReceive.isHasNext() ? "1" : "0");
		for (ConsumptionInstallments consumptionInstallments : consumptionInstallmentsesReceive.getConsumptionInstallmentsList()) {
			// 交易金额---过滤出大于600的数据 借方、贷方---过滤出DEBT表示借方（例如，消费）
			if (Double.parseDouble(consumptionInstallments.getTransactionAmount()) >= Double.parseDouble(consumptionInstallmentMinAmount)
					&& "DEBT".equalsIgnoreCase(consumptionInstallments.getDebitCreditCode())) {
				// 设置币种中文显示
				consumptionInstallments.setCurrencyChinaCode(CurrencyUtil.translateChinese(consumptionInstallments.getOriginalCurrencyCode()));
				// 对金额字段进行处理
				consumptionInstallments.setTransactionAmount(AmtUtil.procString(consumptionInstallments.getTransactionAmount()));
				consumptionInstallments.setOriginalTransactionAmount(AmtUtil.procString(consumptionInstallments.getOriginalTransactionAmount()));
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
		// 对金额字段进行处理
		param.put("transactionAmount", AmtUtil.procMoneyToString(consumptionInstallmentAuthorization.getTransactionAmount()));
		param.put("cardNo", consumptionInstallmentAuthorization.getCardNo());
		param.put("accountNoID", consumptionInstallmentAuthorization.getAccountNoID());
		param.put("installmentPeriods", consumptionInstallmentAuthorization.getInstallmentPeriods());
		param.put("isfeeFlag", consumptionInstallmentAuthorization.getIsfeeFlag());
		ConsumptionInstallmentCostResp consumptionInstallmentCostResp = httpClient.send(costConsumptionInstallmentUrl, param,
				ConsumptionInstallmentCostResp.class);
		if (consumptionInstallmentCostResp == null || consumptionInstallmentCostResp.getData() == null) {
			return null;
		}
		ConsumptionInstallmentCost consumptionInstallmentCost = consumptionInstallmentCostResp.getData();
		// 设置显示币种
		consumptionInstallmentCost.setCurrencyChinaCode(CurrencyUtil.translateChinese(consumptionInstallmentCost.getCurrencyCode()));
		// 对金额字段进行处理
		consumptionInstallmentCost.setInstallmentAmount(consumptionInstallmentCost.getInstallmentAmount());
		return consumptionInstallmentCostResp.getData();
	}

	@Override
	public boolean authorizationConsumptionInstallment(ConsumptionInstallmentAuthorization cia) {
		// 授权结果
		boolean returnRes;
		Map<String, String> param = initGcsParam();
		param.put("accountKeyOne", cia.getAccountKeyOne());
		param.put("accountKeyTwo", cia.getAccountKeyTwo());
		param.put("currencyCode", cia.getCurrencyCode());
		param.put("billDateNo", cia.getBillDateNo());
		// 对金额字段进行处理
		param.put("transactionAmount", AmtUtil.procMoneyToString(cia.getTransactionAmount()));
		param.put("cardNo", cia.getCardNo());
		param.put("accountNoID", cia.getAccountNoID());
		param.put("installmentPeriods", cia.getInstallmentPeriods());
		param.put("isfeeFlag", cia.getIsfeeFlag());
		String tDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		InstallmentInfo bi = new InstallmentInfo(cia.getCardNo(), "消费分期", cia.getCurrencyCode(), cia.getTransactionAmount(), cia.getInstallmentPeriods(),
				cia.getIsfeeFlag(), tDate);
		ConsumerAuthorizationResultResp consumerAuthorizationResultResp = httpClient.send(authorizationConsumptionInstallmentUrl, param,
				ConsumerAuthorizationResultResp.class);
		if (consumerAuthorizationResultResp == null || consumerAuthorizationResultResp.getData() == null) {
			return false;
		}
		String resultCode = consumerAuthorizationResultResp.getData();
		if ("+GC00000".equals(resultCode)) {
			bi.setStatus("1");
			returnRes = true;
		} else {
			bi.setStatus("0");
			returnRes = false;
		}
		try {
			installmentInfoDao.save(bi);
		} catch (Exception e) {
			logger.error("@ConsumptionInstallment@db error:[", e + "]");
		}
		return returnRes;
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
