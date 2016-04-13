package com.yada.wechatbank.base;

import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.CardInfoResp;
import com.yada.wechatbank.model.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QinQiang on 2016/4/12.
 */
@Service
public class BaseService {

    @Autowired
    private HttpClient httpClient;
    @Value("${url.getCardInfos}")
    private String getCardInfos;
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
    //GCS参数
    @Value("${gcs.sessionId}")
    private String gcsSessionId;
    @Value("${gcs.channelId}")
    private String gcsChannelId;

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
     * @return
     */
    public Map<String, String> initGcsParam(){
        Map<String, String> param = new HashMap<>();
        param.put("sessionId",gcsSessionId);
        param.put("channelId",gcsChannelId);
        return param;
    }

    /**
     * 根据证件号和证件类型查询卡列表
     * @param identityNo
     * @param identityType
     * @return
     */
    public List<CardInfo> selectCardNOs(String identityNo, String identityType){
        Map<String, String> map = initGcsParam();
        map.put("idType",identityType);
        map.put("idNum",identityNo);
        CardInfoResp cardInfoResp = httpClient.send(getCardInfos,map,CardInfoResp.class);
        return  cardInfoResp.getCardInfoList();
    }


}
