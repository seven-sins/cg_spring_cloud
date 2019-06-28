package com.cg.shiro.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cg.common.config.redis.RedisService;
import com.cg.common.config.spring.SpringUtil;
import com.cg.po.sys.User;
import com.cg.shiro.config.token.JWTToken;
import com.cg.shiro.config.utils.JWTUtil;

/**
 * @author seven sins
 * @date 2018年5月30日 下午7:54:42
 */
public class AuthRealm extends AuthorizingRealm {
	
	RedisService redisService;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}
	
	/**
	 * 此方法调用hasRole, hasPermission的时候才会进行回调
	 * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * 登录成功时将用户权限列表存入redis, 进入此方法验证权限时从redis取出放入SimpleAuthorizationInfo返回
		 */
		String userId = principals.toString();
		System.out.println(userId);
		/**
		 * 权限列表
		 */
		List<String> permissionList = new ArrayList<>();
		// 例如添加权限编码 user_find
		permissionList.add("user_find");
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 权限
		info.addStringPermissions(permissionList);
		
		List<String> roleList = new ArrayList<>();
		// 角色
		roleList.add("admin");
		info.addRoles(roleList);
		
		return info;
	}

	/**
	 * 认证登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		
		updateToken(token);
		// 解密获得username
		String userId = JWTUtil.getUserId(token);
		String key = ShiroCommon.USER_PREFIX + userId;
		
		if(redisService == null) {
			redisService = (RedisService) SpringUtil.getBean("redisServiceImpl");
		}
		
		User user = (User) redisService.get(key);
		if(user == null) {
			throw new AuthenticationException("session过期");
		}
		if(!JWTUtil.verify(token, userId, user.getUserName())) {
			throw new AuthenticationException("session过期");
		}
		
		return new SimpleAuthenticationInfo(userId, token, getName());
	}
	
	/**
	 * 改为自己刷新token， 不使用jwt的有效期
	 * @param token
	 */
	private void updateToken(String token) {
		if(redisService == null) {
			redisService = (RedisService) SpringUtil.getBean("redisServiceImpl");
		}
		Object expired = redisService.get(token);
		if(expired == null) {
			throw new AuthenticationException("session过期");
		}
		redisService.add(token, System.currentTimeMillis() + ShiroCommon.USER_PREFIX, (int) ShiroCommon.EXPIRE_TIME / (60 * 1000));
	}
}
