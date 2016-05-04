package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.service.WbicCardInfoService;
import com.yada.wechatbank.util.IdTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 海淘卡实现类
 * Created by pangChangSong on 2016/4/21.
 */
@Service
public class WbicCardInfoServiceImpl extends BaseService implements WbicCardInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;

    @Value("${url.getWbicCardInfo}")
    protected String getWbicCardInfo;

    @Value("${url.wbicCardSendSMS}")
    protected String wbicCardSendSMS;

    @Override
    public String getWbicCards(String idNum, String idType) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("idNum", idNum);
        param.put("idType", idType);
        //kafka事件生成
        messageProducer.send(TopicEnum.EBANK_QUERY, "WbicCardInfo_getWbicCards", param);
        //发送请求，查询海淘卡
        StringResp stringResp = httpClient.send(getWbicCardInfo, param, StringResp.class);
        return stringResp == null ? null : stringResp.getData();
    }

    @Override
    public Boolean wbicCardInfoSendSms(String cardNo) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        //发送请求，给海涛用户发送短信
        BooleanResp booleanResp = httpClient.send(wbicCardSendSMS, param, BooleanResp.class);
        return (booleanResp == null || booleanResp.getData() == null) ? false : booleanResp.getData();
    }
}
