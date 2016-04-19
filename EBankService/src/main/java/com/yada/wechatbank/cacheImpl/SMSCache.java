package com.yada.wechatbank.cacheImpl;

import com.yada.wechatbank.cache.ISMSCache;
import com.yada.wechatbank.model.SMSCodeManagement;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

@Service
public class SMSCache implements ISMSCache{
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SMSCache.class);
	@Autowired
	private EhCacheCacheManager cacheManager;
	@Value("${cacheName.SMS}")
	private String cacheName;

	@Override
	public SMSCodeManagement put(String smsKey, SMSCodeManagement smsCodeManagement){
		cacheManager.getCache(cacheName).put(smsKey,smsCodeManagement);
		logger.info("CACHE[{}].put() key=[{}],value=[{}]", cacheName,smsKey,smsCodeManagement);
		return smsCodeManagement;
	}
	
	@Override
	public SMSCodeManagement get(String smsKey){
		ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(smsKey);
		if(valueWrapper == null){
			logger.info("CACHE[{}].get() key=[{}],value=[{}]", cacheName,smsKey,null);
			return null;
		}
		logger.info("CACHE[{}].get() key=[{}],value=[{}]", cacheName,smsKey,valueWrapper.get().toString());
		return (SMSCodeManagement) valueWrapper.get();
	}
	
	@Override
	public void remove(String smsKey){
		logger.info("CACHE[{}].remove() key=[{}]", cacheName,smsKey);
		cacheManager.getCache(cacheName).evict(smsKey);
	}

}
