package com.cg.flink.task;

import java.util.List;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import com.cg.common.config.spring.SpringUtil;
import com.cg.flink.map.CarrierMap;
import com.cg.flink.reduce.CarrierReduce;
import com.cg.flink.service.CarrierService;
import com.cg.po.flink.Carrier;

/**
 * @author seven sins
 * 2019年6月29日 下午2:46:01
 */
public class CarrierTask {
	
	private static CarrierService carrierService;

	public static void main(String[] args) {
		initService();
		
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().setGlobalJobParameters(params);
		DataSet<String> text = env.readTextFile(params.get("input"));
		DataSet<Carrier> map = text.map(new CarrierMap());
		DataSet<Carrier> reduce = map.groupBy("groupField").reduce(new CarrierReduce());
		try {
			List<Carrier> list = reduce.collect();
			for(Carrier carrier : list) {
				carrierService.add(carrier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void initService() {
		if(carrierService == null) {
			carrierService = SpringUtil.getBean("carrierServiceImpl", CarrierService.class);
		}
	}
}
