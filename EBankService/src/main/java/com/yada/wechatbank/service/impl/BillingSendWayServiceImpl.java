package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillSendTypeResp;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BillingSendWayService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillingSendWayServiceImpl extends BaseService implements BillingSendWayService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    // 账单寄送方式查询
    @Value(value = "${url.getBillSendTypeMethod}")
    protected String getBillSendTypeMethod;
    // 账单寄送方式修改
    @Value(value = "${url.updateBillSendTypeMethod}")
    protected String updateBillSendTypeMethod;


    @Override
    public List<BillSendType> getBillSendType(String identityType, String identityNo) {

        List<CardInfo> cardNos = selectCardNos(identityType, identityNo);

        List<BillSendType> list = new ArrayList<>();

        //判断卡是否正常获取
        if (cardNos == null || cardNos.size() == 0) {
            return list;
        }

        Map<String, String> map = initGcsParam();
        for (CardInfo cardBean : cardNos) {
            map.put("cardNo", cardBean.getCardNo());
            BillSendTypeResp br = httpClient.send(getBillSendTypeMethod, map, BillSendTypeResp.class);
            BillSendType b = br == null ? null : br.getBizResult();
            if (b != null) {
                b.setCardNo(cardBean.getCardNo());
                list.add(b);
            } else {
                BillSendType billSendType = new BillSendType();
                billSendType.setCardNo(cardBean.getCardNo());
                list.add(billSendType);
            }
        }

        logger.debug("@ZDJSFSCX@通过卡列表cardNos[{}]获取账单寄送方式集合为[{}]", cardNos, list);

        //如果有数据，则将卡号展示的部分隐藏
        if (list.size() != 0) {
            processShowCardNo(list);
        }
        return list;
    }

    @Override
    public boolean updateBillSendType(String cardNo, String billSendType) {
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        map.put("billSendType", billSendType);
        Boolean b = httpClient.send(getBillSendTypeMethod, map, BooleanResp.class).getBizResult();
        return b == null ? null : b;
    }

    /**
     * 将卡号信息加密，并处理卡号遮盖
     * @param list 账单寄送方式列表
     */
    public void processShowCardNo(List<BillSendType> list) {
        for (BillSendType billSendType : list) {
            try {
                String cardNo = billSendType.getCardNo();
                if (cardNo != null && !"".equals(cardNo)) {
                    billSendType.setCardNo(
                            cardNo.substring(0, 4)
                                    + "********"
                                    + cardNo.substring(cardNo.length() - 4,
                                    cardNo.length()) + ","
                                    + Crypt.encode(cardNo));
                }
            } catch (Exception e) {
                logger.error("@ZDJSFSCX@卡号加密过程中出现错误cardNo["
                        + billSendType.getCardNo() + "]:", e);
            }
        }
    }

}