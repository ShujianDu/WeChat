package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.service.WbicCardInfoService;
import com.yada.wechatbank.util.IdTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${url.getWbicCards}")
    protected String getWbicCards;

    @Value("${url.wbicCardInfoSendSms}")
    protected String wbicCardInfoSendSms;

    @Override
    public String getWbicCards(String idNum, String idType) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("idNum", idNum);
        //转化成gcs证件类型
        param.put("idType", IdTypeUtil.numIdTypeTransformToECode(idType));

        //发送请求，查询海淘卡
        StringResp stringResp = httpClient.send(getWbicCards, param, StringResp.class);
        logger.debug("@HTCX@根据证件号证件类型查询海淘卡，传入的参数为idNum[{}],idType[{}],结果为[{}]",
                idNum, idType, stringResp == null ? null : stringResp.getData());
        return stringResp == null ? null : stringResp.getData();
    }

    @Override
    public Boolean wbicCardInfoSendSms(String cardNo) {
        //获得gcs初始化map
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);

        //发送请求，给海涛用户发送短信
        BooleanResp booleanResp = httpClient.send(wbicCardInfoSendSms, param, BooleanResp.class);
        logger.debug("@HTCX@海淘卡根据卡号发送短信，传入的参数为 cardNo[{}],返回的结果为[{}]", cardNo,
                (booleanResp == null || booleanResp.getData() == null) ? false : booleanResp.getData());
        return (booleanResp == null || booleanResp.getData() == null) ? false : booleanResp.getData();
    }
}
