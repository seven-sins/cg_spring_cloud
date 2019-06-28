package com.cg.shiro.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.cg.shiro.config.filter.JWTFilter;
import com.cg.shiro.config.listener.ShiroSessionListener;

/**
 * @author Rex.Tan
 * 2019年3月26日 上午11:24:41
 */
@Configuration
public class ShiroConfiguration {
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 配置授权登录
	 * @param matcher
	 * @return
	 */
	@Bean("authRealm")
	public AuthRealm authRealm() {
		AuthRealm authRealm = new AuthRealm();
		return authRealm;
	}
	
	@Bean("sessionManager")
	public DefaultWebSessionManager sessionManager() {
		/**
		 * 重写DefaultWebSessionManager获取session方法, 解决单次请求多次访问redis读取session的问题
		 */
		DefaultWebSessionManager sessionManager = new CustomShiroSessionManager();
		CustomRedisSessionDao redisSessionDao = new CustomRedisSessionDao();
		redisSessionDao.setRedisTemplate(redisTemplate);
		sessionManager.setSessionDAO(redisSessionDao);
		
		sessionManager.setGlobalSessionTimeout(ShiroCommon.EXPIRE_TIME);
		sessionManager.setDeleteInvalidSessions(true);
		// sessionManager.setSessionValidationInterval(ShiroCommon.EXPIRE_TIME);
        // sessionManager.setSessionValidationSchedulerEnabled(true);
		
		Collection<SessionListener> listenerList = new ArrayList<>();
		listenerList.add(new ShiroSessionListener());
		sessionManager.setSessionListeners(listenerList);
		
		return sessionManager;
	}
	
	@Bean("securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm, @Qualifier("sessionManager") DefaultWebSessionManager sessionManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		
		// 关闭自带session
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        
		CustomRedisCacheManager redisCacheManager = new CustomRedisCacheManager();
		redisCacheManager.setRedisTemplate(redisTemplate);
		manager.setCacheManager(redisCacheManager);
        
        manager.setSessionManager(sessionManager);
		
		return manager;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager manager) {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		/**
		 * 设置filter
		 */
		Map<String, Filter> filterMap = new HashMap<>();
		filterMap.put("jwt", new JWTFilter());
		factory.setFilters(filterMap);
		/**
		 * 设置securityManager
		 */
		factory.setSecurityManager(manager);
		
		/**
		 * 自定义url规则
		 */
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 必须登录才能访问
		filterChainDefinitionMap.put("index", "authc");
		
		// 必须有admin角色才能访问接口/admin
		filterChainDefinitionMap.put("/admin", "roles[admin]");
		
		// 不需要登录也能访问
		filterChainDefinitionMap.put("/auth/**", "anon");
		filterChainDefinitionMap.put("/401", "anon");
		filterChainDefinitionMap.put("/actuator", "anon");
		filterChainDefinitionMap.put("/**", "jwt");
		factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return factory;
	}
	
	/**
	 * 整合shiro到spring
	 * @param manager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		
		return advisor;
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		
		return creator;
	}
}
