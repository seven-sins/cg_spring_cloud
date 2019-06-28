package com.cg.database.base;

import java.util.List;

/**
 * @author Rex.Tan
 * 2019年3月25日 下午4:31:36
 */
public interface BaseService<T> {

	/**
	 * 带条件查询，条件可以为null，既没有条件；返回list对象集合
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> find(int pageNum, int pageSize, String orderBy, T entity);

	/**
	 * 只查询一个
	 * 
	 * @param id
	 * @return
	 */
	public T get(Long id);

	/**
	 * 插入，用实体作为参数
	 * 
	 * @param entity
	 */
	public void insert(T entity);

	/**
	 * 修改，用实体作为参数
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 按id删除，删除一条；支持整数型和字符串类型ID
	 * 
	 * @param id
	 */
	public void deleteById(Long id);

}
