package com.cg.flink.stream.task;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import com.cg.flink.stream.map.UserVisitMap;
import com.cg.flink.stream.reduce.UserVisitReduce;
import com.cg.flink.stream.sink.UserVisitSink;
import com.cg.flink.stream.transfer.KafkaMessageSchema;
import com.cg.flink.stream.transfer.KafkaMessageWatermarks;
import com.cg.po.bigdata.Message;
import com.cg.po.bigdata.UserVisit;

/**
 * 用户访问实时统计
 * @author Rex.Tan
 * @date 2019年7月30日 下午1:42:57
 */
public class UserVisitTask {
    public static void main(String[] args) {
        args = new String[]{"--input-topic","testTopic","--bootstrap.servers","127.0.0.1:9092",
                "--zookeeper.connect","127.0.0.1:2181","--group.id","myconsumer1","--winsdows.size","50"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < 5) {
            System.out.println("Missing parameters!\n" +
                    "Usage: Kafka --input-topic <topic>" +
                    "--bootstrap.servers <kafka brokers> " +
                    "--zookeeper.connect <zk quorum> --group.id <some id>");
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000); 
        env.getConfig().setGlobalJobParameters(parameterTool); 
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        FlinkKafkaConsumer010<Message>  flinkKafkaConsumer = new FlinkKafkaConsumer010<Message>(parameterTool.getRequired("input-topic"), new KafkaMessageSchema(), parameterTool.getProperties());
        DataStream<Message> input = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));
        
        // 数据转换
        DataStream<UserVisit> map = input.flatMap(new UserVisitMap());
        
        KeyedStream<UserVisit, Tuple> keyBy = map.keyBy("userId");
        
        WindowedStream<UserVisit, Tuple, TimeWindow>  timeWindow = keyBy.timeWindow(Time.seconds(5L));
        
        DataStream<UserVisit> reduce = timeWindow.reduce(new UserVisitReduce());
        // DataStream<UserVisit> reduce = map.keyBy("userId").countWindow(Long.valueOf(parameterTool.getRequired("winsdows.size"))).reduce(new UserVisitReduce());
        
        reduce.addSink(new UserVisitSink()).name("userVisit");
        try {
            env.execute("userVisit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
