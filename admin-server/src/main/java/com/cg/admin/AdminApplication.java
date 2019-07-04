package com.cg.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 服务监控
 * @author Rex.Tan
 * 2019年7月4日 下午2:21:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AdminApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
