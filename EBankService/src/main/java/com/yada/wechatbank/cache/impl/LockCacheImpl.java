package com.yada.wechatbank.cache.impl;

import com.yada.wechatbank.cache.ILockCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/**
 * 锁定缓存
 * Created by QinQiang on 2016/4/22.
 */
@Service
public class LockCacheImpl implements ILockCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EhCacheCacheManager cacheManager;

    @Value("${cacheName.lockCatch}")
    private String cacheName;
    private static final String LOCK = "1";

    @Override
    public String put(String lockKey) {
        logger.info("CACHE[{}].put() key=[{}],value=[{}]", cacheName, lockKey, LOCK);
        cacheManager.getCache(cacheName).put(lockKey, LOCK);
        return lockKey;
    }

    @Override
    public String get(String lockKey) {
        ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(lockKey);
        if (valueWrapper == null) {
            logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, lockKey, null);
            return null;
        }
        logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, lockKey, valueWrapper.get().toString());
        return (String) valueWrapper.get();
    }
}
