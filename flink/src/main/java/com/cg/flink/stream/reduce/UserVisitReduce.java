package com.cg.flink.stream.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;

import com.cg.po.bigdata.UserVisit;

public class UserVisitReduce implements ReduceFunction<UserVisit> {
	private static final long serialVersionUID = 1L;

	@Override
	public UserVisit reduce(UserVisit value1, UserVisit value2) throws Exception {
		value1.setVisitCount(value1.getVisitCount() + value2.getVisitCount());
		
		System.out.println("===================\n");
		System.out.println("userId: " + value1.getUserId() + ", visitCount: " + value1.getVisitCount());
		
		return value1;
	}

}
