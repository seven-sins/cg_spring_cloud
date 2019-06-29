package com.cg.flink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableMvnConfig;

/**
 * 启用mvc配置
 */
@EnableMvnConfig
/**
 * 全局错误拦截
 */
@EnableExceptionHandler
@SpringBootApplication
@EnableDiscoveryClient
public class FlinkApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FlinkApplication.class, args);
	}
}
