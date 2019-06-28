package com.cg.common.config.redis.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cg.common.config.redis.RedisService;

/**
 * redis公共方法
 * @author Rex.Tan
 * 2019年3月26日 上午10:09:08
 */
@Service("redisServiceImpl")
public class RedisServiceImpl implements RedisService {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void add(String id, Object obj) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(id);
		boundOps.set(obj);		
	}

	@Override
	public void add(String id, Object obj, Integer minute) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(id);
		boundOps.set(obj);
		if(minute != null) {
			boundOps.expire(minute, TimeUnit.MINUTES);
		}
	}

	@Override
	public Object get(String key) {
		BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(key);
		return boundOps.get();
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void put(String key, String hashKey, Object obj) {
		redisTemplate.opsForHash().put(key, hashKey, obj);
	}

	@Override
	public Object get(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

}
