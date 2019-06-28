package com.cg.sys.service;

import com.cg.database.base.BaseService;
import com.cg.po.sys.Role;

/**
 * 角色Service
 * @author Rex.Tan
 * 2019年3月25日 下午4:54:43
 */
public interface RoleService extends BaseService<Role> {
	/**
	 * 删除角色
	 * @param id
	 */
	void deleteByRoleId(Long id);
}
