package com.yada.wechatbank.cache;

/**
 * 绑定验证次数缓存
 * Created by QinQiang on 2016/4/22.
 */
public interface ICountBindingCache {
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
    String [] get(String countKey);

    /**
     * 验证失败次数达到五次后，将数据放置到lock缓存中，并删除该计数缓存中的数据
     *
     * @param countKey countKey
     */
    void remove(String countKey);
}
