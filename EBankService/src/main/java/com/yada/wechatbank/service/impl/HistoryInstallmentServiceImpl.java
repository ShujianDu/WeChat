package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.HistoryInstallmentResp;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.model.HistoryInstallmentList;
import com.yada.wechatbank.service.HistoryInstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Echo on 2016/4/12.
 */
@Service
public class HistoryInstallmentServiceImpl extends BaseService implements HistoryInstallmentService {

    @Autowired
    private HttpClient httpClient;
    @Value("${url.getHistoryInstallment}")
    private String getHistoryInstallment;

    @Override
    public HistoryInstallmentList queryHistoryInstallment(String cardNo, String startNumber, String selectNumber) {
        //TODO 调用后台查询分期历史
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        map.put("startNumber", startNumber);
        map.put("selectNumber", selectNumber);
        HistoryInstallmentResp historyInstallmentResp = httpClient.send(getHistoryInstallment, map, HistoryInstallmentResp.class);
        return historyInstallmentResp.getBizResult();
    }

    @Override
    public List<String> selectCardNOs(String identityNo, String identityType) {
        List<CardInfo> cardInfoList = selectCardNos(identityNo, identityType);
        List<String> cardList = new ArrayList<>();
        for (int i = 0; i < cardInfoList.size(); i++) {
            cardList.add(cardInfoList.get(i).getCardNo());
        }
        return cardList;
    }


}
