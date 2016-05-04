package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.service.AllCustomerInfoService;
import com.yada.wx.db.service.dao.AllCustomerInfoDao;
import com.yada.wx.db.service.model.AllCustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 推送管理
 * Created by QinQiang on 2016/4/15.
 */
@Service
@Transactional
public class AllCustomerInfoServiceImpl implements AllCustomerInfoService {

    @Autowired
    private AllCustomerInfoDao allCustomerInfoDao;

    @Autowired
    MessageProducer messageProducer;

    @Override
    public List<AllCustomerInfo> findByIdentityNo(String identityNo) {
        //kafka
        messageProducer.send(TopicEnum.EBANK_QUERY, "AllCustomerInfoFindByIdentityNo", identityNo);
        return allCustomerInfoDao.findByIdentityNo(identityNo);
    }

    @Override
    public boolean updateNoticeByIdentityNo(String notice, String billNotice, String repaymentNotice, String identityNo) {
        messageProducer.send(TopicEnum.EBANK_DO, "AllCustomerInfoUpdateNoticeByIdentityNo", "用户identityNo["+identityNo
                +"]更改动户通知开关为["+notice+"]更改账单提醒开关为["+billNotice+"]更改还款提醒开关为["+repaymentNotice+"]");
        allCustomerInfoDao.updateNoticeByIdentityNo(notice, billNotice, repaymentNotice, identityNo);
        return true;
    }
}
