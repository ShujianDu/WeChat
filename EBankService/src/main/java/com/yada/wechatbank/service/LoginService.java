package com.yada.wechatbank.service;

import com.yada.wx.db.service.model.AuthInfo;
import com.yada.wx.db.service.model.CustomerInfo;

/**
 * Created by echo on 16/5/5.
 */
public interface LoginService {
    /**
     * 根据证件类型和证件号获取手机号码
     *
     * @param idType 证件类型
     * @param idNum  证件号
     * @return 手机号码
     */
    String getMobileNo(String idType, String idNum);

    /**
     * 判断用户是都锁定
     *
     * @param idType 证件类型
     * @param idNum  证件号
     * @return 锁定状态
     */
    boolean isLocked(String idType, String idNum);

    /**
     * 获取用户授权数据
     *
     * @param authCode 授权码
     * @return
     */
    AuthInfo getAuthInfo(String authCode);


    /**
     * 根据openId获取用户数据
     *
     * @param openId openId
     * @return 用户信息
     */
    CustomerInfo getCustomerInfo(String openId);

    /**
     * 根据id删除authInfo
     * * @param id
     */
    void deleteById(Long id);

}
