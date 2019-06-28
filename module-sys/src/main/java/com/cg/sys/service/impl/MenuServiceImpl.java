package com.cg.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.utils.Assert;
import com.cg.database.base.impl.BaseServiceImpl;
import com.cg.mapper.sys.MenuMapper;
import com.cg.po.sys.Menu;
import com.cg.sys.service.MenuService;
import com.cg.utils.idutil.IdMaker;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Rex.Tan
 * 2019年3月25日 下午4:55:48
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

	@Autowired
	MenuMapper menuMapper;
	
	@Resource
	public void setBaseMapper(BaseMapper<Menu> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public void insert(Menu menu) {
		/**
		 * 验证菜单名重名
		 */
		Menu dbMenu = menuMapper.getByMenuName(menu.getMenuName());
		Assert.isTrue(!dbMenu.getParentId().equals(menu.getParentId()), "菜单名已存在");
		menu.setId(IdMaker.get());
		menu.setIsDelete(0);
		menu.setStatus(1);
		menu.setCreateTime(new Date());
		
		menuMapper.insert(menu);
	}
}
