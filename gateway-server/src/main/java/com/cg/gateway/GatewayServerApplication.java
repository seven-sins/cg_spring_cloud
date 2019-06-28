package com.cg.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关服务
 * @author Rex.Tan
 * 2019年3月25日 上午11:54:03
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
}
