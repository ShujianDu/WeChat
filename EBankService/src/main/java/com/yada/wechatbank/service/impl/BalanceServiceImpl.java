package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BalanceResp;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BalanceService;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.CurrencyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 我的额度
 * @author tx
 */
@Service
public class BalanceServiceImpl extends BaseService implements BalanceService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Autowired
    private CurrencyUtil currencyUtil;

    @Value(value = "${url.getBalanceMethod}")
    private String getBalance;

    public List<Balance> getCardNoBalance(String cardNo) {
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);

        BalanceResp balanceResp = httpClient.send(getBalance, param, BalanceResp.class);
        List<Balance> balanceList=balanceResp==null ? null : balanceResp.getBizResult();

        logger.debug("@WDED@通过卡[{}]获取到的额度集合为[{}]", cardNo, balanceList);
        //判断是否获取到额度数据
        if (balanceList == null) {
            return null;
        } else if (balanceList.size() == 0) {
            return new ArrayList<>();
        }

        List<Balance> newList = new LinkedList<>();
        for (int i = 1; i < balanceList.size(); i++) {
            // 人民币放在第一
            if ("CNY".equals(balanceList.get(i).getCurrencyCode())) {
                newList.add(0, balanceList.get(i));
            } else {
                newList.add(balanceList.get(i));
            }
        }
        //替换币种显示
        for(Balance b:newList) {
            b.setCurrencyChinaCode(currencyUtil.translateChinese(b.getCurrencyCode()));
        }
        return newList;
    }



    @Override
    public List<CardInfo> getProessCardNoList(String identityType, String identityNo) {
        List<CardInfo> cardList = selectCardNos(identityType, identityNo);
        if (cardList != null && cardList.size() != 0) {
            try {
                for(CardInfo c:cardList)
                {
                    c.setCardNo(Crypt.cardNoOneEncode(c.getCardNo()));
                }
                return cardList;
            } catch (Exception e) {
                logger.error("@WDED@将卡号进行展示处理时出现错误[" + cardList
                        + "]", e);
            }
        }
        return cardList;
    }
}
