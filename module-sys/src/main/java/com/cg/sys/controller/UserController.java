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

import com.cg.po.sys.User;
import com.cg.sys.service.UserService;
import com.cg.utils.request.Page;
import com.cg.utils.response.Result;
import com.github.pagehelper.PageInfo;

/**
 * 用户控制器
 * @author Rex.Tan
 * 2019年3月25日 下午4:29:44
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	@RequiresPermissions("user_find")
	public Result<List<User>> list(Page page, User user) {
		List<User> list = userService.find(page.getIndex(), page.getSize(), page.getOrderBy(), user);
		
		return new Result<>(list).total(new PageInfo<User>(list).getTotal());
	}
	
	@GetMapping("/user/{id}")
	public Result<User> get(@PathVariable("id") String id) {
		User user = userService.get(id);
		
		return new Result<>(user);
	}
	
	@PostMapping("/user")
	public Result<?> create(@Valid @RequestBody User user) {
		userService.insert(user);
		
		return Result.SUCCESS;
	}
	
	@PutMapping("/user/{id}")
	public Result<?> create(@PathVariable("id") String id, @Valid @RequestBody User user) {
		user.setId(id);
		user.setUpdateTime(new Date());
		userService.update(user);
		
		return Result.SUCCESS;
	}
	
	@DeleteMapping("/user/{id}")
	public Result<?> create(@PathVariable("id") String id) {
		userService.deleteByUserId(id);
		
		return Result.SUCCESS;
	}
}
