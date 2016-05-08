package com.yada.wechatbank.cache;

/**
 * 发送短信次数缓存
 * Created by QinQiang on 2016/4/22.
 */
public interface ICountSMSCache {
    /**
     * 将验证失败的放到缓存中，并返回失败次数
     *
     * @param countKey countKey
     * @param identyNo identyNo
     * @return String
     */
    String put(String countKey, String identyNo);

    /**
     * 取缓存中的数据
     *
     * @param countKey countKey
     * @return String
     */
    String[] get(String countKey);

    /**
     * 验证失败次数达到五次后，将数据放置到lock缓存中，并删除该计数缓存中的数据
     *
     * @param countKey countKey
     */
    void remove(String countKey);

    /**
     * 登录验证失败放入缓存中
     * @param countKey 证件号
     * @param identyType 证件类型
     * @return 失败次数
     */
    String loginPut(String countKey,String identyType);

    /**
     * 获取缓存中存储的登录失败次数
     * @param countKey
     * @return
     */
    String[] getLogin(String countKey);

    /**
     * 移除登录失败次数
     * @param countKey
     */
    void removeLogin(String countKey);

}
