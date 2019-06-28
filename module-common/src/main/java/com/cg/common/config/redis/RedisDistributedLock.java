package com.cg.common.config.redis;

/**
 * 分布式锁
 * @author Rex.Tan
 * 2019年6月18日 下午2:58:39
 */
public interface RedisDistributedLock {
	
	/**
	 * 获取锁
	 * @author Tan Ling
	 * @date 2019年6月18日 上午11:49:40
	 * @param key
	 * @param retryCount 重试次数, 默认5次, 每次间隔200ms
	 * @return
	 */
	public boolean get(String key, Integer retryCount);
	
	/**
	 * 释放锁
	 * @author Tan Ling
	 * @date 2019年6月18日 上午11:22:39
	 * @param key
	 * @return
	 */
	public void release(String key);
}
