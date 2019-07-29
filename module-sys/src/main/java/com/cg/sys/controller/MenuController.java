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

import com.cg.po.sys.Menu;
import com.cg.sys.service.MenuService;
import com.cg.utils.request.Page;
import com.cg.utils.response.Result;
import com.github.pagehelper.PageInfo;

/**
 * 菜单控制器
 * @author Rex.Tan
 * 2019年3月25日 下午4:29:44
 */
@RestController
public class MenuController {

	@Autowired
	MenuService menuService;
	
	@GetMapping("/menu")
	@RequiresPermissions("menu_find")
	public Result<List<Menu>> list(Page page, Menu menu) {
		List<Menu> list = menuService.find(page.getIndex(), page.getSize(), page.getOrderBy(), menu);
		
		return new Result<>(list).total(new PageInfo<Menu>(list).getTotal());
	}
	
	@GetMapping("/menu/{id}")
	public Result<Menu> get(@PathVariable("id") String id) {
		Menu menu = menuService.get(id);
		
		return new Result<>(menu);
	}
	
	@PostMapping("/menu")
	public Result<?> create(@Valid @RequestBody Menu menu) {
		menuService.insert(menu);
		
		return Result.SUCCESS;
	}
	
	@PutMapping("/menu/{id}")
	public Result<?> create(@PathVariable("id") Long id, @Valid @RequestBody Menu menu) {
		menu.setId(id);
		menu.setUpdateTime(new Date());
		menuService.update(menu);
		
		return Result.SUCCESS;
	}
	
	@DeleteMapping("/menu/{id}")
	public Result<?> create(@PathVariable("id") String id) {
		menuService.deleteById(id);
		
		return Result.SUCCESS;
	}
}
