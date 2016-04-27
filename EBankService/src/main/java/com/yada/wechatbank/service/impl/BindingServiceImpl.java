package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.cache.ICountSMSCache;
import com.yada.wechatbank.cache.ILockCache;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.CustMobileResp;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.BindingService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author zm
 */
@Service
@Transactional
public class BindingServiceImpl extends BaseService implements BindingService {

    private final String success = "0";// 成功
    private final String noCard = "1";// 查询不到卡号
    private final String pwdFiled = "2";// 验密失败
    @Value("${bcsp.sms.bsnType}")
    private String bsnType;
    @Value("${sms.bindingContent}")
    private String bindingContent;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private HttpClient httpClient;
    @Value("${url.verificationPWD}")
    private String verificationPWD;
    @Value("${url.getCustMobile}")
    private String getCustMobile;
    @Autowired
    private ICountSMSCache countSMSCacheImpl;
    @Autowired
    private ILockCache LockCacheImpl;

    /**
     * 验证是否已经绑定，返回 成功/失败
     *
     * @param openId openId
     * @return 用户是否绑定
     */
    @Override
    public boolean validateIsBinding(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        if(customerInfo!=null){
            return true;
        }
        return false;
    }

    /**
     * 判断账户是否锁定
     *
     * @param openId openId
     * @param idNum  证件号
     * @return 账户是否锁定
     */
    @Override
    public boolean isLocked(String openId, String idNum) {
        if (LockCacheImpl.get(openId) != null || LockCacheImpl.get(idNum) != null) {
            return true;
        }
        return false;
    }

    /**
     * 计数器缓存加1
     */
    @Override
    public void addCountCache(String openId, String idNum) {
        countSMSCacheImpl.put(openId, idNum);
    }

    /**
     * 身份绑定
     *
     * @param openId   openId
     * @param idType   证件类型
     * @param idCardNo 证件号
     * @param pwd      查询密码
     * @return 绑定结果
     */
    @Override
    public String custBinding(String openId, String idType, String idCardNo, String pwd) {
        String cardNo;
        List<CardInfo> cardInfoList = selectCardNOs(idCardNo, idType);
        if (cardInfoList != null && cardInfoList.size() != 0) {
            cardNo = cardInfoList.get(0).getCardNo();
        } else {
            countSMSCacheImpl.put(openId, idCardNo);
            return noCard;
        }
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        map.put("pwd", pwd);
        //使用卡号密码验密
        BooleanResp booleanResp = httpClient.send(verificationPWD, map, BooleanResp.class);
        if (!booleanResp.getBizResult()) {
            countSMSCacheImpl.put(openId, idCardNo);
            return pwdFiled;
        } else {
            countSMSCacheImpl.remove(openId);
        }
        Map<String, String> mobileMap = initGcsParam();
        mobileMap.put("idType", idType);
        mobileMap.put("idNum", idCardNo);
        //根据证件号和证件类型获取手机号
        CustMobileResp custMobileResp = httpClient.send(getCustMobile, mobileMap, CustMobileResp.class);
        //删除数据库中已绑定此证件号的记录
        List<String> openIds = customerInfoDao.getOpenIdByidentityNo(idCardNo);
        if (openIds != null && openIds.size() != 0) {
            for (String bindedOpenId : openIds) {
                customerInfoDao.deleteByOpenId(bindedOpenId);
            }
        }
        // 开始插入绑定信息
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setOpenId(openId);
        customerInfo.setIdentityType(idType);
        customerInfo.setIdentityNo(idCardNo);
        customerInfo.setMobilePhone(custMobileResp.getBizResult());
        Calendar cal = Calendar.getInstance();
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        customerInfo.setBindingDate(dateStr);
        if (validateIsBinding(openId)) {
            customerInfoDao.deleteByOpenId(openId);
        }
        customerInfoDao.save(customerInfo);
        return success;
    }

    /**
     * 获取所有卡号 绑定默认卡
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return 卡列表
     */
    @Override
    public List<CardInfo> selectCardNOs(String identityNo, String identityType) {
        return selectCardNos(identityNo, identityType);
    }

    /**
     * 查询数据库中的客户信息的证件类型有无数据
     *
     * @param openId openId
     * @return 是否有证件类型
     */
    @Override
    public Map<String, String> isExistIdType(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        Map<String, String> result = new HashMap<>();
        result.put("isexist", "false");
        if (customerInfo != null) {
            String idType = customerInfo.getIdentityType();
            if (idType != null && !"".equals(idType)) {
                result.put("isexist", "true");
            }
            String idNum = customerInfo.getIdentityNo();
            result.put("idNum", idNum);
        }
        return result;
    }

    /**
     * 获取已绑定卡
     *
     * @param openId openId
     * @return 默认卡号
     */
    @Override
    public String getDefCardNo(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        if(customerInfo!=null){
            return customerInfo.getDefCardNo();
        }
        return null;
    }

    /**
     * 绑定默认卡
     *
     * @param openId    openId
     * @param defCardNO 默认卡号
     * @return 绑定结果
     */
    @Override
    public boolean defCardBinding(String openId, String defCardNO) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        if (customerInfo == null) {
            return false;
        }
        customerInfo.setDefCardNo(defCardNO);
        customerInfoDao.save(customerInfo);
        //获取卡列表
        //TODO
        //发事件给动户通知
        //TODO
        return true;
    }

    /**
     * 验证手机号码
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param mobilNo      手机号
     * @return String
     */
    @Override
    public String vaidateMobilNo(String openId, String identityNo, String identityType, String mobilNo) {
        //判断账户是否锁定
        if (LockCacheImpl.get(openId) != null || LockCacheImpl.get(identityNo) != null) {
            return "locked";
        }
        Map<String, String> map = initGcsParam();
        map.put("idType", identityType);
        map.put("idNum", identityNo);
        CustMobileResp custMobileResp = httpClient.send(getCustMobile, map, CustMobileResp.class);
        String mobile = null;
        if (custMobileResp != null) {
            mobile = custMobileResp.getBizResult();
        }
        if (mobile == null) {
            countSMSCacheImpl.put(openId, identityNo);
            return "exception";
        }
        if ("".equals(mobile.trim())) {
            countSMSCacheImpl.put(openId, identityNo);
            return "noMobileNumber";
        }
        if (!mobile.equals(mobilNo)) {
            countSMSCacheImpl.put(openId, identityNo);
            return "wrongMobilNo";
        }
        //证件号手机号输入正确，次数清零
        countSMSCacheImpl.remove(openId);
        return "true";
    }

    /**
     * 补充证件类型是否正确
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return boolean
     */
    @Override
    public boolean isCorrectIdentityType(String identityNo, String identityType) {
        List<CustomerInfo> customerInfoList = customerInfoDao.findByIdentityTypeAndIdentityNo(identityType, identityNo);
        return customerInfoList != null && customerInfoList.size() != 0;
    }

    /**
     * 补充证件类型插入数据库
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return boolean
     */
    public boolean fillIdentityType(String identityType, String identityNo) {
        int result = customerInfoDao.updateIdentityTypeByIdentityNo(identityType, identityNo);
        return result != 0;
    }

}
