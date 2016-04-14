package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BillInstallmentServiceImpl extends BaseService implements BillInstallmentService{
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Override
    public List<String> getProessCardNoList(String identityType,String identityNo) {
        //TODO 通过父类获取卡列表
        List<String> cardList=null;
        if(cardList!=null||cardList.size()!=0)
        {
            try {
                return Crypt.cardNoCrypt(cardList);
            } catch (Exception e) {
                logger.error("@WDZD@将卡号进行展示处理时出现错误[" + cardList
                        + "]", e);
            }
        }
        return cardList;
    }
}
