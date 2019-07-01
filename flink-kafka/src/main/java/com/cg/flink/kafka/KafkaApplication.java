package com.cg.flink.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableMvnConfig;
import com.cg.kafka.annotation.EnableKafkaServer;

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
 */
@EnableKafkaServer
/**
 * 全局错误拦截
 */
@EnableExceptionHandler
@SpringBootApplication
@EnableDiscoveryClient
public class KafkaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}
}
