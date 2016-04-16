package com.yada.wechatbank.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.CardInfoResp;
import com.yada.wechatbank.model.CardInfo;

/**
 * Service父类，提供共用属性和方法 Created by QinQiang on 2016/4/12.
 */
public class BaseService {

	@Autowired
	protected HttpClient httpClient;

	// 直销系统参数
	@Value("${zx.txnId}")
	private String txnId;
	@Value("${zx.deviceType}")
	private String deviceType;
	@Value("${zx.deviceToken}")
	private String deviceToken;
	@Value("${zx.imei}")
	private String imei;
	@Value("${zx.channelNo}")
	private String channelNo;

	// GCS参数
	@Value("${gcs.sessionId}")
	private String gcsSessionId;
	@Value("${gcs.channelId}")
	private String gcsChannelId;

	// 获取卡信息
	@Value("${url.getCardInfos}")
	protected String getCardInfos;
	// 预约办卡进度查询
	@Value(value = "${url.cardApply}")
	protected String cardApplyUrl;
	// 账期查询
	@Value(value = "${url.billingPeriod}")
	protected String billingPeriodUrl;
	// 账单摘要查询
	@Value(value = "${url.billingSummary}")
	protected String billingSummaryUrl;

	/**
	 * 构建直销系统参数
	 *
	 * @return Map<String, String>
	 */
	public Map<String, String> initDirectSaleParam() {
		Map<String, String> param = new HashMap<>();
		param.put("txnId", txnId);
		param.put("deviceType", deviceType);
		param.put("deviceToken", deviceToken);
		param.put("imei", imei);
		param.put("channelNo", channelNo);
		return param;
	}

	/**
	 * 初始化GCS参数
	 *
	 * @return
	 */
	public Map<String, String> initGcsParam() {
		Map<String, String> param = new HashMap<>();
		param.put("sessionId", gcsSessionId);
		param.put("channelId", gcsChannelId);
		return param;
	}

	/**
	 * 根据证件号和证件类型查询卡列表
	 *
	 * @param identityNo
	 * @param identityType
	 * @return
	 */
	public List<CardInfo> selectCardNos(String identityNo, String identityType) {
		Map<String, String> map = initGcsParam();
		map.put("idType", identityType);
		map.put("idNum", identityNo);
		CardInfoResp cardInfoResp = httpClient.send(getCardInfos, map, CardInfoResp.class);
		return cardInfoResp.getBizResult();
	}

}
