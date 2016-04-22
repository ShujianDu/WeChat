package com.yada.wechatbank.cache;

/**
 * 锁定缓存
 * Created by QinQiang on 2016/4/22.
 */
public interface ILockCache {
    /**
     * 将绑定失败的数据放到缓存中，并返回被锁的数据
     *
     * @param lockKey lockKey
     * @return String
     */
    String put(String lockKey);

    /**
     * String get(String lockKey);
     *
     * @param lockKey lockKey
     * @return String
     */
    String get(String lockKey);
}
