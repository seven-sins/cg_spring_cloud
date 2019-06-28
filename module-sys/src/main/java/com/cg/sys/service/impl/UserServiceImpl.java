package com.cg.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.utils.Assert;
import com.cg.database.base.impl.BaseServiceImpl;
import com.cg.mapper.sys.UserMapper;
import com.cg.po.sys.User;
import com.cg.sys.service.UserService;
import com.cg.utils.idutil.IdMaker;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Rex.Tan
 * 2019年3月25日 下午4:55:48
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Resource
	public void setBaseMapper(BaseMapper<User> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public void insert(User user) {
		/**
		 * 验证用户名重名
		 */
		Assert.isTrue(userMapper.getByUserName(user.getUserName()) == null, "用户名已存在");
		user.setId(IdMaker.get());
		user.setIsDelete(0);
		user.setStatus(1);
		user.setCreateTime(new Date());
		
		userMapper.insert(user);
	}

	@Override
	public User findByUsername(String userName) {
		return userMapper.getByUserName(userName);
	}

	@Override
	public void deleteByUserId(Long userId) {
		userMapper.deleteByUserId(userId);
	}
}
