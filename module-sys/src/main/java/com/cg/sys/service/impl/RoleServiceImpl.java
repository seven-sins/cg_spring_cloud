package com.cg.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.utils.Assert;
import com.cg.database.base.impl.BaseServiceImpl;
import com.cg.mapper.sys.RoleMapper;
import com.cg.po.sys.Role;
import com.cg.sys.service.RoleService;
import com.cg.utils.idutil.IdMaker;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Rex.Tan
 * 2019年3月25日 下午4:55:48
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	
	@Resource
	public void setBaseMapper(BaseMapper<Role> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public void insert(Role role) {
		Assert.isTrue(roleMapper.getByRoleName(role.getRoleName()) == null, "角色名称已存在");
		role.setId(IdMaker.getStringKey());
		role.setIsDelete(0);
		role.setStatus(1);
		role.setCreateTime(new Date());
		
		roleMapper.insert(role);
	}
	
	@Override
	public void update(Role role) {
		Role dbRole = roleMapper.getByRoleName(role.getRoleName());
		Assert.isTrue(dbRole == null || dbRole.getId().equals(role.getId()), "角色名称已存在");
		role.setUpdateTime(new Date());
		baseMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void deleteByRoleId(String roleId) {
		roleMapper.deleteByRoleId(roleId);
	}
}
