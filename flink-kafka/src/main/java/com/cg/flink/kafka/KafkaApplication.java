package com.cg.flink.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cg.common.annotation.EnableKafkaServer;
import com.cg.common.annotation.EnableMvnConfig;

/**
 * @author seven sins
 * 2019年6月28日 下午11:17:19
 */
/**
 * 启用mvc配置
 */
@EnableMvnConfig
/**
 * 启用kafka配置
 * @author seven sins
 * 2019年6月29日 上午1:03:34
 */
@EnableKafkaServer
@SpringBootApplication
@EnableDiscoveryClient
public class KafkaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}
}
