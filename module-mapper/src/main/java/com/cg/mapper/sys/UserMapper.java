package com.cg.mapper.sys;

import org.apache.ibatis.annotations.Mapper;

import com.cg.po.sys.User;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 用户Mapper
 * @author Rex.Tan
 * 2019年3月25日 下午4:18:20
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{

	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	User getByUserName(String userName);

	/**
	 * 删除用户
	 * @param id
	 */
	void deleteByUserId(String id);
}
