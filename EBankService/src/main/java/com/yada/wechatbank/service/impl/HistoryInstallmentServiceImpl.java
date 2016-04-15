package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.HistoryInstallmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Echo on 2016/4/12.
 */
@Service
public class HistoryInstallmentServiceImpl extends BaseService implements HistoryInstallmentService {


    @Override
    public Map<String, Object> queryHistoryInstallment(String cardNo, String startNumber, String selectNumber) {
        Map<String, Object> map = null;
        //TODO 调用后台查询分期历史
        return map;
    }

    @Override
    public List<String> selectCardNOs(String identityNo,String identityType) {
        List<CardInfo> cardInfoList = selectCardNos(identityNo,identityType);
        List<String> cardList = new ArrayList<>();
        for (int i=0;i<=cardInfoList.size();i++){
            cardList.add(cardInfoList.get(i).getCardNo());
        }
        return cardList;
    }


}
