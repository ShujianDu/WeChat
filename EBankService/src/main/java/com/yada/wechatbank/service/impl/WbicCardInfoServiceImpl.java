package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.ListStringResp;
import com.yada.wechatbank.service.WbicCardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 海淘卡实现类
 * Created by pangChangSong on 2016/4/21.
 */
@Service
public class WbicCardInfoServiceImpl extends BaseService implements WbicCardInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${url.getWbicCardsMethod}")
    protected String getWbicCardsMethod;

    @Value("${url.wbicCardInfoSendSmsMethod}")
    protected String wbicCardInfoSendSmsMethod;

    @Override
    public List<String> getWbicCards(String idNum, String idType) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("idNum", idNum);
        param.put("idType", idType);

        //发送请求，查询海淘卡
        ListStringResp listStringResp = httpClient.send(getWbicCardsMethod, param, ListStringResp.class);
        logger.debug("@HTCX@根据证件号证件类型查询海淘卡，传入的参数为idNum[{}],idType[{}],结果为[{}]",
                idNum, idType, listStringResp == null ? null : listStringResp.getBizResult());
        return listStringResp == null ? null : listStringResp.getBizResult();
    }

    @Override
    public Boolean wbicCardInfoSendSms(String cardNo) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);

        //发送请求，给海涛用户发送短信
        BooleanResp booleanResp = httpClient.send(wbicCardInfoSendSmsMethod, param, BooleanResp.class);
        logger.debug("@HTCX@海淘卡根据卡号发送短信，传入的参数为 cardNo[{}],返回的结果为[{}]", cardNo,
                booleanResp == null ? false : booleanResp.getBizResult());
        return booleanResp == null ? false : booleanResp.getBizResult();
    }
}