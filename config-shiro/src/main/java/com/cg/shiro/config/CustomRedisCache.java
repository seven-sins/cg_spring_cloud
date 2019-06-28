package com.cg.shiro.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import com.cg.common.config.spring.SpringUtil;

/**
 * @author Rex.Tan
 * 2019年3月26日 上午11:15:38
 */
@SuppressWarnings("unchecked")
public class CustomRedisCache<K, V> implements Cache<K, V> {
	
	RedisTemplate<String, V> redisTemplate;
	
	public CustomRedisCache() {
		
	}
	
	@Override
	public void clear() throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
        // 这里不用connection.flushDb(), 以免Session等其他缓存数据被连带删除
        Set<String> redisKeys = redisTemplate.keys(ShiroCommon.SESSION_PREFIX + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
	}

	@Override
	public V get(K key) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		return redisTemplate.opsForValue().get(ShiroCommon.SESSION_PREFIX + key);
	}

	@Override
	public Set<K> keys() {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> redisKeys = redisTemplate.keys(ShiroCommon.SESSION_PREFIX + "*");
        Set<K> keys = new HashSet<K>();
        for (String redisKey : redisKeys) {
            keys.add((K) redisKey.substring(ShiroCommon.SESSION_PREFIX.length()));
        }
        return keys;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		V previos = get(key);
        redisTemplate.opsForValue().set(ShiroCommon.SESSION_PREFIX + key, value, ShiroCommon.EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return previos;
	}

	@Override
	public V remove(K key) throws CacheException {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		V previos = get(key);
        redisTemplate.delete(ShiroCommon.SESSION_PREFIX + key);
        return previos;
	}

	@Override
	public int size() {
		if (keys() == null) {
			return 0;
		}
        return keys().size();
	}

	@Override
	public Collection<V> values() {
		if(redisTemplate == null) {
			redisTemplate = (RedisTemplate<String, V>) SpringUtil.getBean("redisTemplate");
		}
		Set<String> redisKeys = redisTemplate.keys(ShiroCommon.SESSION_PREFIX + "*");
        Set<V> values = new HashSet<V>();
        for (String redisKey : redisKeys) {
            V value = redisTemplate.opsForValue().get(redisKey);
            values.add(value);
        }
        return values;
	}

}
