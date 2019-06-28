package com.cg.shiro.config;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:22:10
 */
public class ShiroCommon {

	/**
	 * redis中session前缀
	 */
	public static final String SESSION_PREFIX = "shiro_session_";
	/**
	 * 用户信息前缀
	 */
	public static final String USER_PREFIX = "shiro_user_";
	/**
	 * session全局超时
	 */
	public static final long EXPIRE_TIME = 1800000L;
}
