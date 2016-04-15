package com.yada.wechatbank.service.impl;

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

    @Override
    public List<AllCustomerInfo> findByIdentityNo(String identityNo) {
        return allCustomerInfoDao.findByIdentityNo(identityNo);
    }

    @Override
    public boolean updateNoticeByIdentityNo(String notice, String billNotice, String repaymentNotice, String identityNo) {
        try {
            allCustomerInfoDao.updateNoticeByIdentityNo(notice, billNotice, repaymentNotice, identityNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
