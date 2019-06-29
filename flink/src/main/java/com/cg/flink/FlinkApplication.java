package com.cg.flink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableMvnConfig;
import com.cg.elasticsearch.annotation.EnableElasticsearch;

/**
 * 启用mvc配置
 */
@EnableMvnConfig
/**
 * 全局错误拦截
 */
@EnableExceptionHandler
/**
 * Elasticsearch
 */
@EnableElasticsearch
@SpringBootApplication
@EnableDiscoveryClient
public class FlinkApplication {
	
	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false"); // 不加这行es启动报错
		SpringApplication.run(FlinkApplication.class, args);
	}
}
