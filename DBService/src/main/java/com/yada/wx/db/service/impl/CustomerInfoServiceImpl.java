package com.yada.wx.db.service.impl;

import com.yada.wx.db.service.CustomerInfoService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
@Service
@Transactional
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private CustomerInfoDao customerInfoDao;

    @Override
    public boolean isBinded(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        return customerInfo == null ? false : true;
    }

    @Override
    public boolean bind(String openId, String identityNo, String identityType, String pwd, String mobilePhone) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setBindingDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityType(identityType);
        customerInfo.setIdentityNo(identityNo);
        customerInfo.setMobilePhone(mobilePhone);
        try {
            customerInfoDao.save(customerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean defCardBinding(String openId, String defCardNo) {
        try {
            CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
            customerInfo.setDefCardNo(defCardNo);
            customerInfoDao.saveAndFlush(customerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public CustomerInfo getCustomerInfoByOpenId(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        return customerInfo;
    }

    @Override
    public boolean deleteByOpenId(String openId) {
        try {
            CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
            customerInfoDao.delete(customerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<CustomerInfo> getCustInfoByIdentityNo(String identityNo) {
        List<CustomerInfo> list = customerInfoDao.findByIdentityNo(identityNo);
        return list;
    }

    @Override
    public boolean updateIdentityTypeByIdentityNo(String identityNo, String identityType) {
        List<CustomerInfo> list = customerInfoDao.findByIdentityNo(identityNo);
        for (int i = 0; i < list.size(); i++) {
            CustomerInfo customerInfo = list.get(i);
            customerInfo.setIdentityType(identityType);
            customerInfoDao.save(customerInfo);
        }
        return true;
    }
}
