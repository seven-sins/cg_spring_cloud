package com.cg.flink.map;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import com.cg.flink.entity.BrandLike;
import com.cg.flink.kafka.KafkaEvent;

public class BrandlikeMap implements FlatMapFunction<KafkaEvent, BrandLike> {
	private static final long serialVersionUID = 1L;

	@Override
	public void flatMap(KafkaEvent kafkaEvent, Collector<BrandLike> out) throws Exception {
		String data = kafkaEvent.getWord();
		
	}

}
