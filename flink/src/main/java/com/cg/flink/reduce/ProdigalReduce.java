package com.cg.flink.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;

import com.cg.po.flink.Prodigal;

/**
 * @author seven sins
 * 2019年6月29日 下午4:50:30
 */
public class ProdigalReduce implements ReduceFunction<Prodigal> {
	private static final long serialVersionUID = 1L;

	@Override
	public Prodigal reduce(Prodigal prodigal, Prodigal t1) throws Exception {
		return prodigal;
	}
	
}
