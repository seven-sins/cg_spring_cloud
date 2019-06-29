package com.cg.flink.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.cg.flink.kafka.listener.KafkaCustomListener;

/**
 * kafka消费者配置
 * @author seven sins
 * 2019年6月29日 上午9:56:01
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.consumer.servers}")
	private String servers;
	@Value("${kafka.consumer.enable.auto.commit}")
	private Boolean enableAutoCommit;
	@Value("${kafka.consumer.session.timeout}")
	private String sessionTimeout;
	@Value("${kafka.consumer.auto.commit.interval}")
	private String autoCommitInterval;
	@Value("${kafka.consumer.group.id}")
	private String groupId;
	@Value("${kafka.consumer.auto.offset.reset}")
	private String autoOffsetReset;
	@Value("${kafka.consumer.concurrency}")
	private int concurrency;
	
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(concurrency);
		factory.getContainerProperties().setPollTimeout(1500);
		
		return factory;
	}
	
	public ConsumerFactory<String, String> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}
	
	public Map<String, Object> consumerConfigs(){
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		
		return props;
	}
	
	/**
	 * 自定义listener
	 * @return
	 */
	@Bean
	public KafkaCustomListener listener() {
		return new KafkaCustomListener();
	}
}
