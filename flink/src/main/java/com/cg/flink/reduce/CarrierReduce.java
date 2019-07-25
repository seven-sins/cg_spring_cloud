//package com.cg.flink.reduce;
//
//import org.apache.flink.api.common.functions.ReduceFunction;
//
//import com.cg.po.flink.Carrier;
//
///**
// * @author seven sins
// * 2019年6月29日 下午4:50:30
// */
//public class CarrierReduce implements ReduceFunction<Carrier> {
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public Carrier reduce(Carrier carrier, Carrier t1) throws Exception {
//		Carrier finalyCarrier = new Carrier();
//		finalyCarrier.setCarrier(carrier.getCarrier());
//		finalyCarrier.setCount(carrier.getCount() + t1.getCount());
//		
//		return finalyCarrier;
//	}
//}
