package com.cg.database.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cg.database.base.BaseService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Rex.Tan
 * 2019年3月25日 下午4:37:24
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected BaseMapper<T> baseMapper;
	
	/**
	 * 子类必须给baseMapper赋值
	 * @param baseMapper
	 */
	public abstract void setBaseMapper(BaseMapper<T> baseMapper);
	
	@Override
	public List<T> find(int pageNum, int pageSize, String orderBy, T entity) {
		if(StringUtils.isBlank(orderBy)) {
			PageHelper.startPage(pageNum, pageSize);
		} else {
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
		
		return baseMapper.select(entity);
	}

	@Override
	public T get(Long id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(T entity) {
		baseMapper.insert(entity);
	}

	@Override
	public void update(T entity) {
		baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void deleteById(Long id) {
		baseMapper.deleteByPrimaryKey(id);
	}

}
