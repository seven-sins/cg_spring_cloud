package com.cg.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.common.config.redis.RedisService;
import com.cg.common.exception.CustomException;
import com.cg.po.sys.User;
import com.cg.shiro.config.ShiroCommon;
import com.cg.shiro.config.utils.JWTUtil;
import com.cg.sys.service.UserService;
import com.cg.utils.response.Result;

/**
 * @author Rex.Tan
 * 2019年3月26日 上午11:33:54
 */
@RestController
public class AuthController {
	private static final String USER_NOT_FOUND = "用户或密码错误";
	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
	@PostMapping("/auth/login")
	public Result<?> login(@RequestBody User user) {
		User userBean = userService.findByUsername(user.getUserName());
		if(userBean == null) {
			throw new CustomException(401, USER_NOT_FOUND);
		}
		if(!userBean.getPassword().equals(user.getPassword())) {
			throw new CustomException(401, USER_NOT_FOUND);
		}
		/**
		 * 将当前登录用户信息存入redis
		 * key: ShiroCommon.USER_PREFIX + username
		 */
		String key = ShiroCommon.USER_PREFIX + userBean.getId();
		redisService.delete(key);
		redisService.add(key, userBean);
		String token = JWTUtil.sign(userBean.getUserName(), userBean.getId());
		redisService.add(token, System.currentTimeMillis() + ShiroCommon.USER_PREFIX, (int) ShiroCommon.EXPIRE_TIME / (60 * 1000));
		
		return new Result<>(token);
	}
	
	@GetMapping("/auth/logout")
	public Result<?> logout(){
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			String username = (String) subject.getPrincipal();
			String key = ShiroCommon.USER_PREFIX + username;
			redisService.delete(key);
			//
			subject.logout();
		}
		return Result.SUCCESS;
	}
}