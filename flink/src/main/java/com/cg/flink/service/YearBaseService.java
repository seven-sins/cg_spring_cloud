package com.cg.flink.service;

import com.alibaba.fastjson.JSONObject;
import com.cg.po.flink.YearBase;

/**
 * @author seven sins
 * 2019年6月30日 下午12:20:34
 */
public interface YearBaseService {
	
	/**
	 * 添加
	 * seven sins
	 * 2019年6月30日 下午12:23:53
	 * @param yearBase
	 */
	void add(YearBase yearBase);
	
	/**
	 * 根据ID查询
	 * seven sins
	 * 2019年6月30日 下午12:41:35
	 * @param id
	 * @return
	 */
	public JSONObject get(String id);
}
