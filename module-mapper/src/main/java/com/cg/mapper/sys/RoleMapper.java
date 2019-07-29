package com.cg.mapper.sys;

import org.apache.ibatis.annotations.Mapper;

import com.cg.po.sys.Role;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 角色Mapper
 * @author Rex.Tan
 * 2019年3月25日 下午4:18:20
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role>{

	/**
	 * 根据角色名查询用户
	 * @param roleName
	 * @return
	 */
	Role getByRoleName(String roleName);

	/**
	 * 删除角色
	 * @param id
	 */
	void deleteByRoleId(String id);
}
