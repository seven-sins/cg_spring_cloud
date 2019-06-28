package com.cg.sys.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.po.sys.Role;
import com.cg.sys.service.RoleService;
import com.cg.utils.request.Page;
import com.cg.utils.response.Result;
import com.github.pagehelper.PageInfo;

/**
 * 角色控制器
 * @author Rex.Tan
 * 2019年3月25日 下午4:29:44
 */
@RestController
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@GetMapping("/role")
	@RequiresPermissions("role_find")
	public Result<List<Role>> list(Page page, Role role) {
		List<Role> list = roleService.find(page.getIndex(), page.getSize(), page.getOrderBy(), role);
		
		return new Result<>(list).total(new PageInfo<Role>(list).getTotal());
	}
	
	@GetMapping("/role/{id}")
	public Result<Role> get(@PathVariable("id") Long id) {
		Role role = roleService.get(id);
		
		return new Result<>(role);
	}
	
	@PostMapping("/role")
	public Result<?> create(@Valid @RequestBody Role role) {
		roleService.insert(role);
		
		return Result.SUCCESS;
	}
	
	@PutMapping("/role/{id}")
	public Result<?> create(@PathVariable("id") Long id, @Valid @RequestBody Role role) {
		role.setId(id);
		role.setUpdateTime(new Date());
		roleService.update(role);
		
		return Result.SUCCESS;
	}
	
	@DeleteMapping("/role/{id}")
	public Result<?> create(@PathVariable("id") Long id) {
		roleService.deleteByRoleId(id);
		
		return Result.SUCCESS;
	}
}
