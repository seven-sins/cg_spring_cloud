package com.cg.flink.stream.sink;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import com.cg.po.bigdata.UserVisit;

public class UserVisitSink implements SinkFunction<UserVisit>  {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public void invoke(UserVisit value, Context context) throws Exception {
		// 先查询是否已经存在, 如果不存在, 创建
		
		// 如果已存在, 更新
		
	}
}
