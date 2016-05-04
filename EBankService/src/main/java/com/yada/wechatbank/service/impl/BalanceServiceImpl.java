package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BalanceResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BalanceService;
import com.yada.wechatbank.util.AmtUtil;
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
 *
 * @author tx
 */
@Service
public class BalanceServiceImpl extends BaseService implements BalanceService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;


    @Value("${url.getCardBalance}")
    private String getCardBalance;

    public List<Balance> getCardNoBalance(String cardNo) {
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);

        messageProducer.send(TopicEnum.EBANK_QUERY, "BalanceGetCardNoBalance", param);

        BalanceResp balanceResp = httpClient.send(getCardBalance, param, BalanceResp.class);
        List<Balance> balanceList = balanceResp == null ? null : balanceResp.getData();

        //判断是否获取到额度数据
        if (balanceList == null) {
            logger.info("@Balance@通过卡[{}]获取到的额度集合为null", cardNo);
            //kafka事件记录
            messageProducer.send(TopicEnum.EBANK_QUERY, "Balance", "通过卡["+cardNo+"]获取到的额度集合为null");
            return null;
        } else if (balanceList.size() == 0) {
            logger.info("@Balance@通过卡[{}]获取到的额度集合长度为0", cardNo);
            //kafka事件记录
            messageProducer.send(TopicEnum.EBANK_QUERY, "Balance", "通过卡[" + cardNo + "]获取到的额度集合长度为0");
            return new ArrayList<>();
        }

        List<Balance> newList = new LinkedList<>();
        for (Balance aBalanceList : balanceList) {
            // 人民币放在第一
            if ("CNY".equals(aBalanceList.getCurrencyCode())) {
                newList.add(0, aBalanceList);
            } else {
                newList.add(aBalanceList);
            }
        }
        //替换币种显示
        for (Balance b : newList) {
            b.setCurrencyChinaCode(CurrencyUtil.translateChinese(b.getCurrencyCode()));
            b.setPeriodAvailableCreditLimit(AmtUtil.procString(b.getPeriodAvailableCreditLimit()));
            b.setPreCashAdvanceCreditLimit(AmtUtil.procString(b.getPreCashAdvanceCreditLimit()));
            b.setWholeCreditLimit(AmtUtil.procString(b.getWholeCreditLimit()));
        }



        return newList;
    }


    @Override
    public List<CardInfo> getProessCardNoList(String identityType, String identityNo) {
        List<CardInfo> cardList = selectCardNos(identityType, identityNo);
        if (cardList != null && cardList.size() != 0) {
            try {
                for (CardInfo c : cardList) {
                    c.setCardNo(Crypt.cardNoOneEncode(c.getCardNo()));
                }
                return cardList;
            } catch (Exception e) {
                logger.error("@Balance@将卡号进行展示处理时出现错误[" + cardList
                        + "]", e);
            }
        }
        return cardList;
    }
}
