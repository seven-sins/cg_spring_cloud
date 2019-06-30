package com.cg.flink.service;

import com.alibaba.fastjson.JSONObject;
import com.cg.po.flink.Carrier;

/**
 * @author seven sins
 * 2019年6月30日 下午12:20:34
 */
public interface CarrierService {
	
	/**
	 * 添加
	 * seven sins
	 * 2019年6月30日 下午12:23:53
	 * @param carrier
	 */
	void add(Carrier carrier);
	
	/**
	 * 根据ID查询
	 * seven sins
	 * 2019年6月30日 下午12:41:35
	 * @param id
	 * @return
	 */
	public JSONObject get(String id);
}
