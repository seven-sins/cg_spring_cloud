package com.cg.shiro.config;

import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.RedisTemplate;

import com.cg.common.config.spring.SpringUtil;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:21:55
 */
public class CustomRedisCacheManager implements CacheManager, Destroyable {

	RedisTemplate<String, Object> redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public void destroy() throws Exception {
		if (redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> keys = redisTemplate.keys(ShiroCommon.SESSION_PREFIX + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new CustomRedisCache<>();
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
