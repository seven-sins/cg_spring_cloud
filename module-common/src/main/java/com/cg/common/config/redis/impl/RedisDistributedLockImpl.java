package com.cg.common.config.redis.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cg.common.config.redis.RedisDistributedLock;
import com.cg.common.exception.CustomException;

import redis.clients.jedis.JedisCommands;

/**
 * 分布式锁
 * @author Rex.Tan
 * 2019年6月18日 下午3:09:10
 */
@Component("redisDistributedLockImpl")
public class RedisDistributedLockImpl implements RedisDistributedLock {
	static final Logger LOG = LoggerFactory.getLogger(RedisDistributedLockImpl.class);
	private static final String LOCK_PREFIX = "redis_lock";
	private static final int RETRY_COUNT = 10;
	private static final int EXPIRE_TIME = 2000;
    
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean get(String key,  Integer retryCount) {
		boolean lock = lock(key);
        if (lock) {
            return true;
        } else {
            // 设置失败次数计数器, 当到达retryCount次时, 返回失败
            int failCount = 1;
            int retry = retryCount == null ? RETRY_COUNT : retryCount;
            while(failCount <= retry){
                // 等待200ms重试
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    LOG.error("=============sleep出错, error: ", e);
                }
                if (lock(key)){
                   return true;
                }else{
                    failCount++;
                }
            }
            throw new CustomException("系统繁忙, 请稍候再试");
        }
	}

	@Override
	public void release(String key) {
		redisTemplate.delete(LOCK_PREFIX + key);
	}

	private boolean lock(String key) {
		RedisCallback<String> callback = (connection) -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            return commands.set(LOCK_PREFIX + key, "empty", "NX", "PX", EXPIRE_TIME);
        };
        String result = redisTemplate.execute(callback);

        return !StringUtils.isEmpty(result);
	}
}
