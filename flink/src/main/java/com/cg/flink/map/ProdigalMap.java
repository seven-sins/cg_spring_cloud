package com.cg.flink.map;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import com.alibaba.fastjson.JSONObject;
import com.cg.po.flink.Prodigal;

/**
 * @author seven sins
 * 2019年6月29日 下午2:54:46
 */
public class ProdigalMap implements MapFunction<String, Prodigal> {
	private static final long serialVersionUID = 1L;

	@Override
	public Prodigal map(String arg) throws Exception {
		if(StringUtils.isBlank(arg)) {
			return null;
		}
		Prodigal prodigal = JSONObject.parseObject(arg, Prodigal.class);
		String groupField = "prodigal==" + prodigal.getUserId();
		prodigal.setGroupField(groupField);
		
		return prodigal;
	}
}
