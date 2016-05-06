package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillSendTypeResp;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BillingSendWayService;
import com.yada.wechatbank.util.BillSendTypeUtil;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillingSendWayServiceImpl extends BaseService implements BillingSendWayService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;

    // 账单寄送方式查询
    @Value("${url.getBillSendType}")
    protected String getBillSendType;
    // 账单寄送方式修改
    @Value("${url.updateBillSendType}")
    protected String updateBillSendType;


    @Override
    public List<BillSendType> getBillSendType(String identityType, String identityNo) {

        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSendWay_getBillSendType","证件类型["+identityType+"]证件号["+identityNo+"]查询卡列表" );
        List<CardInfo> cardNos = selectCardNos(identityType, identityNo);

        List<BillSendType> list = new ArrayList<>();

        //判断卡是否正常获取
        if (cardNos == null || cardNos.size() == 0) {
            return list;
        }

        Map<String, String> map = initGcsParam();
        for (CardInfo cardBean : cardNos) {
            map.put("cardNo", cardBean.getCardNo());
            StringResp br = httpClient.send(getBillSendType, map, StringResp.class);
            String billsend = br == null ? null : br.getData();
            BillSendType billSendType = new BillSendType();
            billSendType.setCardNo(cardBean.getCardNo());
            if (billsend != null) {
                billSendType.setBillSendType(billsend);
                billSendType.setBillSendTypeDesc(BillSendTypeUtil.billSenTypeTransformToDes(billsend));
            }
            list.add(billSendType);
        }
        try {
            //如果有数据，则将卡号展示的部分隐藏
            if (list.size() != 0) {
                for (BillSendType billSendType : list) {
                    processShowCardNo(billSendType);
                }
            }
        } catch (Exception e) {
            logger.error("@ZDJSFSCX@卡号加密过程中出现错误", e);
            return null;
        }
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSendWay_getBillSendType",list);
        return list;
    }

    @Override
    public boolean updateBillSendType(String cardNo, String billSendType) {
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        map.put("billSendType", billSendType);
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSendWay_updateBillSendType", map);
        BooleanResp booleanResp = httpClient.send(updateBillSendType, map, BooleanResp.class);
        Boolean b = booleanResp == null ? null : booleanResp.getData();
        return b == null ? false : b;
    }

    /**
     * 将卡号信息加密，并处理卡号遮盖
     *
     * @param billSendType 账单寄送方式
     */
    @Override
    public void processShowCardNo(BillSendType billSendType) throws Exception {
        String cardNo = billSendType.getCardNo();
        if (cardNo != null && !"".equals(cardNo)) {
            billSendType.setCardNo(
                    cardNo.substring(0, 4)
                            + "********"
                            + cardNo.substring(cardNo.length() - 4,
                            cardNo.length()) + ","
                            + Crypt.encode(cardNo));
        }
    }
}
