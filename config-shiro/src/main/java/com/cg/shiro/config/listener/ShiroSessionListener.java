package com.cg.shiro.config.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroSessionListener extends SessionListenerAdapter {
	static final Logger LOG = LoggerFactory.getLogger(ShiroSessionListener.class);
	
	@Override
	public void onStart(Session session) {
		LOG.info("=============会话创建");
	}

	@Override
	public void onStop(Session session) {
		LOG.info("=============会话停止");
	}

	@Override
	public void onExpiration(Session session) {
		LOG.info("=============会话过期");
	}

}
