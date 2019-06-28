package com.cg.common.config.kafka;

/**
 * kafka消息生产者接口
 * @author seven sins
 * 2019年6月29日 上午1:19:16
 */
public interface KafkaProducerService {
	
	/**
	 * 消息发送
	 * @param topic
	 * @param key
	 * @param data
	 */
	void send(String topic, String key, String data);
}
