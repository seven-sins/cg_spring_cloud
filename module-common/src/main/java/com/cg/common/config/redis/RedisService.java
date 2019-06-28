package com.cg.common.config.redis;

/**
 * redis
 * @author Rex.Tan
 * 2019年3月26日 上午10:07:05
 */
public interface RedisService {

	public void add(String id, Object obj);
	
	public void add(String id, Object obj, Integer minute);

	public Object get(String key);

	public void delete(String key);

	public void put(String key, String hashKey, Object obj);

	public Object get(String key, String hashKey);
}
