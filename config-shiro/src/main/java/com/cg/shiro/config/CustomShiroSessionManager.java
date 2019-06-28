package com.cg.shiro.config;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.common.utils.Assert;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:21:44
 */
public class CustomShiroSessionManager extends DefaultWebSessionManager {

	static final Logger LOG = LoggerFactory.getLogger(DefaultSessionManager.class);
	
	/**
	 * 获取session
	 * 优化单次请求需要多次访问redis读取session的问题
	 */
	@Override
	protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
		Serializable sessionId = getSessionId(sessionKey);

		ServletRequest request = null;
		if (sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey) sessionKey).getServletRequest();
		}

		if (request != null && null != sessionId) {
			Object sessionObj = request.getAttribute(sessionId.toString());
			if (sessionObj != null) {
				return (Session) sessionObj;
			}
		}

		Session session = super.retrieveSession(sessionKey);
		
		if (request != null && null != sessionId) {
			request.setAttribute(sessionId.toString(), session);
		}
		
		return session;
	}
	
	@Override
	protected void onChange(Session session) {
		if(session instanceof SimpleSession) {
			SimpleSession simpleSession = (SimpleSession) session;
			Assert.isTrue(!simpleSession.isExpired(), "session过期");
		}
    }
	
}
