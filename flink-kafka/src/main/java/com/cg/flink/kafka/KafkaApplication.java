package com.cg.flink.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.common.annotation.EnableExceptionHandler;
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
 * 全局错误拦截
 */
@EnableExceptionHandler
@SpringBootApplication
public class KafkaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}
}
