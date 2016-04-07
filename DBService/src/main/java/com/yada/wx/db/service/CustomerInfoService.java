package com.yada.wx.db.service;

import java.util.List;

import com.yada.wx.db.service.model.CustomerInfo;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface CustomerInfoService {

    /**
     * 用户是否绑定
     *
     * @param openId openID
     * @return false-未绑定，true-绑定
     */
    public boolean isBinded(String openId);

    /**
     * 用户绑定
     *
     * @param openId       openID
     * @param identityNo   证件号
     * @param identityType 证件类型
     * @param pwd          电话银行密码
     * @return false-失败，true-成功
     */
    public boolean bind(String openId, String identityNo, String identityType, String pwd, String mobilePhone);

    /**
     * 默认卡绑定
     *
     * @param openId    openID
     * @param defCardNo 默认卡号
     * @return false-失败，true-成功
     */
    public boolean defCardBinding(String openId, String defCardNo);

    /**
     * 通过openId获取客户信息
     *
     * @param openId openID
     * @return CustomerInfo
     */
    public CustomerInfo getCustomerInfoByOpenId(String openId);

    /**
     * 通过openID删除
     *
     * @param openId openID
     * @return false-失败，true-成功
     */
    public boolean deleteByOpenId(String openId);

    /**
     * 通过证件号获取客户信息列表
     *
     * @param identityNo 证件号
     * @return List<CustomerInfo>
     */
    public List<CustomerInfo> getCustInfoByIdentityNo(String identityNo);

    /**
     * 通过证件号更新证件类型
     *
     * @param identityNo
     * @param identityType
     * @return false-失败，true-成功
     */
    public boolean updateIdentityTypeByIdentityNo(String identityNo, String identityType);

}
