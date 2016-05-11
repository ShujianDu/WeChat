package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardInfo;
import com.yada.wx.db.service.model.CustomerInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zm
 */
public interface BindingService {
    /**
     * 验证是否已经绑定，返回 成功/失败
     *
     * @param openId openId
     * @return 用户是否绑定
     */
    boolean validateIsBinding(String openId);

    /**
     * 判断账户是否锁定
     *
     * @param openId openId
     * @param idNum  证件号
     * @return 账户是否锁定
     */
    boolean isLocked(String openId, String idNum);

    /**
     * 计数器缓存加1
     */
    void addCountCache(String openId, String idNum);

    /**
     * 身份绑定
     *
     * @param openId   openId
     * @param idType   证件类型
     * @param idCardNo 证件号
     * @param pwd      查询密码
     * @return 身份绑定结果
     */
    String custBinding(String openId, String idType, String idCardNo, String pwd);

    /**
     * 获取所有卡号 绑定默认卡
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return 卡列表
     */
    List<CardInfo> selectCardNOs(String identityType, String identityNo);

    /**
     * 查询数据库中的客户信息的证件类型有无数据
     *
     * @param openId openId
     * @return 是否有证件类型
     */
    Map<String, String> isExistIdType(String openId);

    /**
     * 获取已绑定卡
     *
     * @param openId openId
     * @return 默认卡
     */
    String getDefCardNo(String openId);

    /**
     * 绑定默认卡
     *
     * @param openId    openId
     * @param defCardNO 默认卡号
     * @return 绑定默认卡成功或失败
     */
    boolean defCardBinding(String openId, String defCardNO);

    /**
     * 获取绑定时的短信验证码
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return 手机号码
     */
    String vaidateMobilNo(String openId, String identityNo, String identityType, String mobilNo);


    /**
     * 补充证件类型是否正确
     *
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @return 补充证件类型结果
     */
    boolean isCorrectIdentityType(String identityNo, String identityType);

    /**
     * 补充证件类型插入数据库是否成功
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return 补充证件类型插入数据库是否结果
     */
    boolean fillIdentityType(String identityType, String identityNo);

    /**
     * 根据opId获取客户信息
     *
     * @param openId
     * @return
     */
    CustomerInfo findCustomerInfoByOpenId(String openId);
    /**
     * 通过DB获取授权后的openId
     *
     * @param authcode 授权码
     * @return openId
     */
     String findOpenIdByAuthCode(String authcode);

}
