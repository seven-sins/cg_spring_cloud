package com.cg.flink.task;

import java.util.List;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import com.cg.flink.map.YearBaseMap;
import com.cg.flink.reduce.YearBaseReduce;
import com.cg.po.flink.YearBase;

/**
 * @author seven sins
 * 2019年6月29日 下午2:46:01
 */
public class YearBaseTask {

	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().setGlobalJobParameters(params);
		DataSet<String> text = env.readTextFile(params.get("input"));
		DataSet<YearBase> map = text.map(new YearBaseMap());
		DataSet<YearBase> reduce = map.groupBy("groupField").reduce(new YearBaseReduce());
		try {
			List<YearBase> list = reduce.collect();
			for(YearBase yearBase : list) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
