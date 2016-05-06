package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.cache.ICountSMSCache;
import com.yada.wechatbank.cache.ILockCache;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.CustMobileResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.BindingService;
import com.yada.wechatbank.util.IdTypeUtil;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String success = "0";// 成功
    private final String noCard = "1";// 查询不到卡号
    private final String pwdFiled = "2";// 验密失败
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
    @Autowired
    private MessageProducer messageProducer;
    @Value("${url.getCardHolderInfo}")
    private String getCardHolderInfo;

    /**
     * 验证是否已经绑定，返回 成功/失败
     *
     * @param openId openId
     * @return 用户是否绑定
     */
    @Override
    public boolean validateIsBinding(String openId) {
        CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
        return customerInfo != null;
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
        String result;
        CustomerInfo customerInfo = new CustomerInfo();
        List<CardInfo> cardInfoList = selectCardNOs(idType, idCardNo);
        if (cardInfoList != null && cardInfoList.size() != 0) {
            cardNo = cardInfoList.get(0).getCardNo();
            logger.info("@BD@用户身份绑定,根据证件号idCardNo[{}]证件类型idType[{}]获取到的卡号cardNo[{}]", idCardNo, idType, cardNo);
            Map<String, String> map = new HashMap<>();
            map.put("cardNo", cardNo);
            map.put("pwd", pwd);
            //使用卡号密码验密
            BooleanResp booleanResp = httpClient.send(verificationPWD, map, BooleanResp.class);
            if (booleanResp == null) {
                result = "exception";
            } else if (!"00".equals(booleanResp.getReturnCode())) {
                logger.warn("@BD@验证密码返回错误错误码[{}]", booleanResp.getReturnCode());
                result = "exception";
            } else if (!booleanResp.getData()) {
                logger.info("@BD@用户身份绑定,使用卡号查询密码验密失败,idCardNo[{}]证件类型idType[{}]卡号cardNo[{}]", idCardNo, idType, cardNo);
                countSMSCacheImpl.put(openId, idCardNo);
                result = pwdFiled;
            } else {
                logger.info("@BD@用户身份绑定,使用卡号查询密码验密成功,idCardNo[{}]证件类型idType[{}]卡号cardNo[{}]", idCardNo, idType, cardNo);
                countSMSCacheImpl.remove(openId);
                Map<String, String> mobileMap = initGcsParam();
                mobileMap.put("idType", IdTypeUtil.numIdTypeTransformToECode(idType));
                mobileMap.put("idNum", idCardNo);
                //根据证件号和证件类型获取手机号
                CustMobileResp custMobileResp = httpClient.send(getCustMobile, mobileMap, CustMobileResp.class);
                if (custMobileResp == null) {
                    result = "exception";
                } else if (!"00".equals(custMobileResp.getReturnCode())) {
                    result = "exception";
                } else {
                    //删除数据库中已绑定此证件号的记录
                    List<String> openIds = customerInfoDao.getOpenIdByidentityNo(idCardNo);
                    if (openIds != null && openIds.size() != 0) {
                        logger.info("@BD@用户身份绑定,使用openId删除已绑定数据,idCardNo[{}]", idCardNo);
                        for (String bindedOpenId : openIds) {
                            customerInfoDao.deleteByOpenId(bindedOpenId);
                        }
                    }
                    // 开始插入绑定信息
                    customerInfo.setOpenId(openId);
                    customerInfo.setIdentityType(idType);
                    customerInfo.setIdentityNo(idCardNo);
                    customerInfo.setMobilePhone(custMobileResp.getData());
                    Calendar cal = Calendar.getInstance();
                    String dateStr = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
                    customerInfo.setBindingDate(dateStr);
                    if (validateIsBinding(openId)) {
                        customerInfoDao.deleteByOpenId(openId);
                    }
                    logger.info("@BD@用户身份绑定,往数据库保存信息" + customerInfo.toString());
                    customerInfoDao.save(customerInfo);
                    result = success;
                }
            }
        } else {
            logger.info("@BD@用户身份绑定,根据证件号idCardNo[{}]证件类型idType[{}]获取卡列表失败", idCardNo, idType);
            countSMSCacheImpl.put(openId, idCardNo);
            result = noCard;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("customerInfo", customerInfo);
        map.put("result", result);
        messageProducer.send(TopicEnum.EBANK_DO, "BindingCustBinding", customerInfo);
        return result;
    }

    /**
     * 获取所有卡号 绑定默认卡
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return 卡列表
     */
    @Override
    public List<CardInfo> selectCardNOs(String identityType, String identityNo) {
        return selectCardNos(identityType, identityNo);
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
            logger.info("@BD@查询客户信息有无证件类型openId[{}]", openId);
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
        if (customerInfo != null) {
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
            logger.info("@BD@绑定默认卡,未获取到用户数据openId[{}]defCardNO[{}]", openId, defCardNO);
            return false;
        }
        customerInfo.setDefCardNo(defCardNO);
        CustomerInfo res = customerInfoDao.save(customerInfo);
        if (res == null) {
            return false;
        }
        messageProducer.send(TopicEnum.EBANK_DO, "BindingBindingDef", customerInfo);
        return true;
    }

    /**
     * 验证手机号码
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param mobileNo     手机号
     * @return String
     */
    @Override
    public String vaidateMobilNo(String openId, String identityNo, String identityType, String mobileNo) {
        String result;
        //判断账户是否锁定
        if (LockCacheImpl.get(openId) != null || LockCacheImpl.get(identityNo) != null) {
            logger.info("@BD@验证手机号码,用户已锁定openId[{}]identityNo[{}]", openId, identityNo);
            return "locked";
        }
        Map<String, String> map = initGcsParam();
        map.put("idType", identityType);
        map.put("idNum", identityNo);
        CustMobileResp custMobileResp = httpClient.send(getCustMobile, map, CustMobileResp.class);
        if (custMobileResp == null) {
            result = "exception";
        } else {

            if (!"00".equals(custMobileResp.getReturnCode())) {
                result = "exception";
            } else {
                String mobile = custMobileResp.getData();
                if (mobile == null) {
                    logger.info("@BD@验证手机号码,后台获取手机号为空openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                    countSMSCacheImpl.put(openId, identityNo);
                    result = "exception";
                } else if ("".equals(mobile.trim())) {
                    logger.info("@BD@验证手机号码,后台获取手机号为值错误openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                    countSMSCacheImpl.put(openId, identityNo);
                    result = "noMobileNumber";
                } else if (!mobile.equals(mobileNo)) {
                    logger.info("@BD@验证手机号码,与后台获取手机号不符openId[{}]identityNo[{}]identityType[{}]", openId, identityNo, identityType);
                    countSMSCacheImpl.put(openId, identityNo);
                    result = "wrongMobilNo";
                } else {
                    //证件号手机号输入正确，次数清零
                    countSMSCacheImpl.remove(openId);
                    result = "true";
                }
            }
        }
        map.put("result", result);
        messageProducer.send(TopicEnum.EBANK_QUERY, "BindingVaidateMobilNo", map);
        return result;

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
        Map<String, String> map = new HashMap<>();
        map.put("identityType", identityType);
        map.put("identityNo", identityNo);
        messageProducer.send(TopicEnum.EBANK_DO, "BindingFillIdentityType", map);
        return result != 0;
    }

    @Override
    public CustomerInfo findCustomerInfoByOpenId(String openId) {
        return customerInfoDao.findByOpenId(openId);
    }

}
