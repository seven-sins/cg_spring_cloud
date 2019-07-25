//package com.cg.flink.reduce;
//
//import org.apache.flink.api.common.functions.ReduceFunction;
//
//import com.cg.po.flink.YearBase;
//
///**
// * @author seven sins
// * 2019年6月29日 下午4:50:30
// */
//public class YearBaseReduce implements ReduceFunction<YearBase> {
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public YearBase reduce(YearBase yearBase, YearBase t1) throws Exception {
//		YearBase finalyYearBase = new YearBase();
//		finalyYearBase.setYearType(yearBase.getYearType());
//		finalyYearBase.setCount(yearBase.getCount() + t1.getCount());
//		
//		return finalyYearBase;
//	}
//}
