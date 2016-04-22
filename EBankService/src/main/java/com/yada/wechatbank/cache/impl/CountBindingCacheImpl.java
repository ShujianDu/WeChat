package com.yada.wechatbank.cache.impl;

import com.yada.wechatbank.cache.ICountBindingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/**
 * 绑定验证次数缓存
 * Created by QinQiang on 2016/4/22.
 */
@Service
public class CountBindingCacheImpl implements ICountBindingCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EhCacheCacheManager cacheManager;
    @Autowired
    private LockCacheImpl lockCache;
    @Value("${cacheName.countBinding}")
    private String cacheName;
    //超过5次锁定证件号
    @Value("${cacheName.count}")
    private String COUNT;

    @Override
    public String put(String countKey, String identyNo) {
        String[] countBinding = get(countKey);
        //如果是第一次失败，则新建数组，并将其放入缓存
        if (countBinding == null) {
            countBinding = new String[6];
            countBinding[0] = "1";
            countBinding[1] = identyNo;
            logger.debug("CACHE[{}].put() key=[{}],value=[{}]", cacheName, countKey, countBinding);
            cacheManager.getCache(cacheName).put(countKey, countBinding);
            return countBinding[0];
        }
        //计数次数增加1
        int count = Integer.valueOf(countBinding[0]) + 1;
        countBinding[0] = String.valueOf(count);
        //将证件号放到数组中
        countBinding[count] = identyNo;
        cacheManager.getCache(cacheName).put(countKey, countBinding);
        //设置次数为5次，可在配置文件中修改
        if (count >= Integer.valueOf(COUNT)) {
            lockCache.put(countKey);
            for (int i = 1; i < countBinding.length; i++) {
                lockCache.put(countBinding[i]);
            }
            remove(countKey);
        }
        return countBinding[0];
    }

    @Override
    public String[] get(String countKey) {
        ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(countKey);
        if (valueWrapper == null) {
            logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, countKey, null);
            return null;
        }
        logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, countKey, valueWrapper.get().toString());
        return (String[]) valueWrapper.get();
    }

    @Override
    public void remove(String countKey) {
        logger.debug("CACHE[{}].remove() key=[{}]", cacheName, countKey);
        cacheManager.getCache(cacheName).evict(countKey);
    }
}
