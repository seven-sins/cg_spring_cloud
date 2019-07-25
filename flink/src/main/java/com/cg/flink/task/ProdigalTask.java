//package com.cg.flink.task;
//
//import java.util.List;
//
//import org.apache.flink.api.java.DataSet;
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.api.java.utils.ParameterTool;
//
//import com.cg.common.config.spring.SpringUtil;
//import com.cg.flink.map.ProdigalMap;
//import com.cg.flink.reduce.ProdigalReduce;
//import com.cg.flink.service.CarrierService;
//import com.cg.po.flink.Prodigal;
//
///**
// * 败家指数, 挥霍指数
// * @author seven sins
// * 2019年6月29日 下午2:46:01
// */
//public class ProdigalTask {
//	
//	private static CarrierService carrierService;
//
//	public static void main(String[] args) {
//		initService();
//		
//		final ParameterTool params = ParameterTool.fromArgs(args);
//		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//		env.getConfig().setGlobalJobParameters(params);
//		DataSet<String> text = env.readTextFile(params.get("input"));
//		DataSet<Prodigal> map = text.map(new ProdigalMap());
//		DataSet<Prodigal> reduce = map.groupBy("groupField").reduce(new ProdigalReduce());
//		try {
//			List<Prodigal> list = reduce.collect();
//			for(Prodigal prodigal : list) {
//				// carrierService.add(prodigal);
//				System.out.println(prodigal);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private static void initService() {
//		if(carrierService == null) {
//			carrierService = SpringUtil.getBean("carrierServiceImpl", CarrierService.class);
//		}
//	}
//}
