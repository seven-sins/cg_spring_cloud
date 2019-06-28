package com.cg.shiro.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:21:50
 */
public class CustomRedisSessionDao extends AbstractSessionDAO {

    RedisTemplate<String, Object> redisTemplate;
	
	public CustomRedisSessionDao() {  
        super();  
    } 
	
	public CustomRedisSessionDao(RedisTemplate<String, Object> redisTemplate) {  
        super();  
        this.redisTemplate = redisTemplate;  
    } 
	
	@Override
	public void delete(Session session) {
		if (null == session) {  
            return;  
        }  
        redisTemplate.delete(ShiroCommon.SESSION_PREFIX + session.getId());  
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		// System.out.println("===============update================");  
        if (session == null || session.getId() == null) {  
            return;  
        }  
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
		//        
        // session.setTimeout(ShiroCommon.EXPIRE_TIME);  
        // session.touch();
        
        BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(ShiroCommon.SESSION_PREFIX + session.getId());
		boundOps.set(session);
		boundOps.expire(session.getTimeout(), TimeUnit.SECONDS);
	}

	@Override
	protected Serializable doCreate(Session session) {
		// System.out.println("===============doCreate================");  
        Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
        session.setTimeout(ShiroCommon.EXPIRE_TIME);
        
        BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(ShiroCommon.SESSION_PREFIX + session.getId());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(session.getTimeout(), TimeUnit.MILLISECONDS);
        
        return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		// System.out.println("==============doReadSession=================");  
        if (sessionId == null) {  
            return null;  
        }  
        // System.out.println(sessionId);
        BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(ShiroCommon.SESSION_PREFIX + sessionId);
        Session session = (Session) boundOps.get(); 
        
        return session; 
	}

	/**
	 * 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，
	 * 可以在将session加入redis时指定一个session前缀，
	 * 统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合  
	 */
	@Override
    public Collection<Session> getActiveSessions() {  
        // System.out.println("==============getActiveSessions=================");  
        Set<String> keys = redisTemplate.keys(ShiroCommon.SESSION_PREFIX + "*");  
        Set<Session> sessions = new HashSet<>();
        for(String key: keys) {
        	BoundValueOperations<String, Object> boundOps = redisTemplate.boundValueOps(key);
        	sessions.add((Session)boundOps.get());
        }
        return sessions;
    } 
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
