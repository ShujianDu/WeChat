package com.yada.wechatbank.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QinQiang on 2016/4/12.
 */
@Service
public class BaseService {

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
}
