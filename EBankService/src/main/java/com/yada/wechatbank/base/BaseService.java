package com.yada.wechatbank.base;

import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.CardInfoResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.model.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    // 获取用户在发卡的手机号
    @Value("${url.getCustMobile}")
    protected String getCustMobile;


    /**
     * 构建直销系统参数
     *
     * @return 初始化直销参数后的map对象
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
     * @return 初始化GCS参数后的map对象
     */
    public Map<String, String> initGcsParam() {
        Map<String, String> param = new HashMap<>();
        param.put("tranSessionID", gcsSessionId);
        param.put("reqChannelID", gcsChannelId);
        return param;
    }

    /**
     * 根据证件号和证件类型查询卡列表
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return List<CardInfo>
     */
    public List<CardInfo> selectCardNos(String identityType, String identityNo) {
        Map<String, String> map = initGcsParam();
        map.put("idType", identityType);
        map.put("idNum", identityNo);
        CardInfoResp cardInfoResp = httpClient.send(getCardInfos, map, CardInfoResp.class);
        return cardInfoResp == null ? null : cardInfoResp.getBizResult();
    }

    /**
     * 根据证件号和证件类型查询卡号列表
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return List<String>
     */
    public List<String> selectCardNoList(String identityType, String identityNo) {
        Map<String, String> map = initGcsParam();
        map.put("idType", identityType);
        map.put("idNum", identityNo);
        CardInfoResp cardInfoResp = httpClient.send(getCardInfos, map, CardInfoResp.class);
        if (cardInfoResp == null) {
            return null;
        }
        List<String> cardNoList = new ArrayList<>();
        for (int i = 0; i < cardInfoResp.getBizResult().size(); i++) {
            cardNoList.add(cardInfoResp.getBizResult().get(i).getCardNo());
        }
        return cardNoList;
    }


        /**
         * 根据证件号和证件类型查询手机号
         *
         * @param identityType 证件类型
         * @param identityNo   证件号
         * @return             手机号
         */
        public String getCustMobileNo(String identityType, String identityNo) {
            Map<String, String> map = initGcsParam();
            map.put("idType", identityType);
            map.put("idNum", identityNo);
            StringResp stringResp= httpClient.send(getCustMobile, map, StringResp.class);
            return stringResp == null ? null : stringResp.getData();
        }

}
