package com.cg.flink.task;

import javax.annotation.Nullable;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import com.cg.flink.kafka.KafkaEvent;
import com.cg.flink.kafka.KafkaEventSchema;

public class BrandlikeTask {

	public static void main(String[] args) {
		args = new String[] { "--input-topic", "testTopic", "--bootstrap.servers", "192.168.0.205:9092",
				"--zookeeper.connect", "192.168.0.205:2181", "--group.id", "myconsumer" };
		final ParameterTool parameterTool = ParameterTool.fromArgs(args);

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().disableSysoutLogging();
		env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
		env.enableCheckpointing(5000);
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		DataStream<KafkaEvent> input = env
				.addSource(new FlinkKafkaConsumer011<>(parameterTool.getRequired("input-topic"), new KafkaEventSchema(),
						parameterTool.getProperties()).assignTimestampsAndWatermarks(new CustomWatermarkExtractor()));
		
	}

	private static class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaEvent> {

		private static final long serialVersionUID = -742759155861320823L;

		private long currentTimestamp = Long.MIN_VALUE;

		@Override
		public long extractTimestamp(KafkaEvent event, long previousElementTimestamp) {
			this.currentTimestamp = event.getTimestamp();
			return event.getTimestamp();
		}

		@Nullable
		@Override
		public Watermark getCurrentWatermark() {
			return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
		}
	}
}
