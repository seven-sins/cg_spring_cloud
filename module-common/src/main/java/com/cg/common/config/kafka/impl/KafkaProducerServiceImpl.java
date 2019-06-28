package com.cg.common.config.kafka.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cg.common.config.kafka.KafkaProducerService;

/**
 * kafka消息生产者
 * @author seven sins
 * 2019年6月29日 上午1:20:06
 */
@Component
public class KafkaProducerServiceImpl implements KafkaProducerService {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public void send(String topic, String key, String data) {
		kafkaTemplate.send(topic, key, data);
	}

}
