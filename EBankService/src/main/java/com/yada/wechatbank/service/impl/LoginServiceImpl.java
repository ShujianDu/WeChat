package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.cache.ICountSMSCache;
import com.yada.wechatbank.cache.ILockCache;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.CustMobileResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.service.LoginService;
import com.yada.wx.db.service.dao.AuthInfoDao;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.AuthInfo;
import com.yada.wx.db.service.model.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by echo on 16/5/5.
 */
@Service
@Transactional
public class LoginServiceImpl extends BaseService implements LoginService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${url.getCustMobile}")
    private String getCustMobile;
    @Autowired
    private HttpClient httpClient;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private ICountSMSCache countSMSCacheImpl;
    @Autowired
    private ILockCache LockCacheImpl;
    @Autowired
    private AuthInfoDao authInfoDao;
    @Autowired
    private CustomerInfoDao customerInfoDao;


    @Override
    public String getMobileNo(String idType, String idNum) {
        String result;
        String mobileNo;
        Map<String, String> map = initGcsParam();
        map.put("idType", idType);
        map.put("idNum", idNum);
        CustMobileResp custMobileResp = httpClient.send(getCustMobile, map, CustMobileResp.class);

        if (custMobileResp == null) {
            logger.warn("@DL@获取手机号码发生错误idTYpe[{}]idNum[{}]",idType,idNum);
            result = "exception";
        } else if (!"00".equals(custMobileResp.getReturnCode())) {
            logger.warn("@DL@获取手机号码httpClient返回错误码idTYpe[{}]idNum[{}]returnCode[{}]",idType,idNum,custMobileResp.getReturnCode());
            result = "exception";
        } else {
            mobileNo = custMobileResp.getData();
            if (mobileNo == null) {
                logger.warn("@DL@获取手机号码为空idTYpe[{}]idNum[{}]",idType,idNum);
                countSMSCacheImpl.loginPut(idNum, idType);
                result = "exception";
            } else if ("".equals(mobileNo.trim())) {
                logger.warn("@DL@获取手机号码为'',idTYpe[{}]idNum[{}]",idType,idNum);
                countSMSCacheImpl.loginPut(idNum, idType);
                result = "noMobileNumber";
            } else {
                //证件号输入正确，次数清零
                countSMSCacheImpl.removeLogin(idNum);
                result = mobileNo;
            }
        }
        map.put("result",result);
        messageProducer.send(TopicEnum.EBANK_QUERY,"LoginGetMobileNo",map);
        return result;
    }


    @Override
    public boolean isLocked(String idType, String idNum) {
        if (LockCacheImpl.get(idType) != null || LockCacheImpl.get(idNum) != null) {
            return true;
        }
        return false;
    }

    @Override
    public AuthInfo getAuthInfo(String authCode){
        return authInfoDao.findByAuthCode(authCode);
    }


    @Override
    public CustomerInfo getCustomerInfo(String openId) {
        return customerInfoDao.findByOpenId(openId);
    }

    @Override
    public void deleteById(Long id) {

        authInfoDao.delete(id);
    }
}
