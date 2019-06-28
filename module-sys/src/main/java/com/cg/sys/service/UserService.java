package com.cg.sys.service;

import com.cg.database.base.BaseService;
import com.cg.po.sys.User;

/**
 * 用户Service
 * @author Rex.Tan
 * 2019年3月25日 下午4:54:43
 */
public interface UserService extends BaseService<User> {
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	User findByUsername(String userName);
	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteByUserId(Long userId);
}
