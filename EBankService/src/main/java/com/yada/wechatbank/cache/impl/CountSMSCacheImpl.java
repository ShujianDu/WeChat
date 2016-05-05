package com.yada.wechatbank.cache.impl;

import com.yada.wechatbank.cache.ICountSMSCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/**
 * 发送短信次数缓存
 * Created by QinQiang on 2016/4/22.
 */
@Service
public class CountSMSCacheImpl implements ICountSMSCache {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EhCacheCacheManager cacheManager;
    @Autowired
    private LockCacheImpl lockCache;
    @Value("${cacheName.countSMSCode}")
    private String cacheName;
    //超过5次锁定证件号
    @Value("${cacheName.count}")
    private String COUNT;
    @Value("${cacheName.loginCount}")
    private String LOGINCOUNT;
    @Override
    public String put(String countKey, String identyNo) {
        String[] countSms = get(countKey);
        //如果是第一次失败，则新建数组，并将其放入缓存
        if (countSms == null) {
            countSms = new String[6];
            countSms[0] = "1";
            countSms[1] = identyNo;
            logger.debug("CACHE[{}].put() key=[{}],value=[{}]", cacheName, countKey, countSms);
            cacheManager.getCache(cacheName).put(countKey, countSms);
            return countSms[0];
        }
        //计数次数增加1
        int count = Integer.valueOf(countSms[0]) + 1;
        countSms[0] = String.valueOf(count);
        //将证件号放到数组中
        countSms[count] = identyNo;
        cacheManager.getCache(cacheName).put(countKey, countSms);
        //设置次数为5次，可在配置文件中修改
        if (count >= Integer.valueOf(COUNT)) {
            lockCache.put(countKey);
            for (int i = 1; i < countSms.length; i++) {
                lockCache.put(countSms[i]);
            }
            remove(countKey);
        }
        return countSms[0];
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

    @Override
    public String loginPut(String countKey, String identyType) {
        String[] countSms = get(countKey);
        //如果是第一次失败，则新建数组，并将其放入缓存
        if (countSms == null) {
            countSms = new String[6];
            countSms[0] = "1";
            countSms[1] = identyType;
            logger.debug("CACHE[{}].put() key=[{}],value=[{}]", cacheName, countKey, countSms);
            cacheManager.getCache(cacheName).put(countKey, countSms);
            return countSms[0];
        }
        //计数次数增加1
        int count = Integer.valueOf(countSms[0]) + 1;
        countSms[0] = String.valueOf(count);
        //将证件类型放到数组中
        countSms[count] = identyType;
        cacheManager.getCache(cacheName).put(countKey, countSms);
        //设置次数为5次，可在配置文件中修改
        if (count >= Integer.valueOf(LOGINCOUNT)) {
            lockCache.put(countKey);
            for (int i = 1; i < countSms.length; i++) {
                lockCache.put(countSms[i]);
            }
            remove(countKey);
        }
        return countSms[0];
    }

    @Override
    public String[] getLogin(String countKey) {
        ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(countKey);
        if (valueWrapper == null) {
            logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, countKey, null);
            return null;
        }
        logger.debug("CACHE[{}].get() key=[{}],value=[{}]", cacheName, countKey, valueWrapper.get().toString());
        return (String[]) valueWrapper.get();
    }

    @Override
    public void removeLogin(String countKey) {
        logger.debug("CACHE[{}].remove() key=[{}]", cacheName, countKey);
        cacheManager.getCache(cacheName).evict(countKey);
    }
}
