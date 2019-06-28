package com.cg.mapper.sys;

import org.apache.ibatis.annotations.Mapper;

import com.cg.po.sys.Menu;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * 菜单Mapper
 * @author Rex.Tan
 * 2019年3月25日 下午4:18:20
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu>{

	/**
	 * 根据菜单名查询菜单
	 * @param menuName
	 * @return
	 */
	Menu getByMenuName(String menuName);
}
