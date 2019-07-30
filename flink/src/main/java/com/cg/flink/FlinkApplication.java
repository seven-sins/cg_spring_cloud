package com.cg.flink;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableMvnConfig;
import com.cg.common.annotation.EnableSpringUtil;

/**
 * springUtil工具
 */
@EnableSpringUtil
/**
 * 启用mvc配置
 */
@EnableMvnConfig
/**
 * 全局错误拦截
 */
@EnableExceptionHandler
@SpringBootApplication
public class FlinkApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		// System.setProperty("es.set.netty.runtime.available.processors", "false"); // 不加这行es启动报错
		SpringApplication.run(FlinkApplication.class, args);
	}

	// 部署  --classpath file:///home/hadoop/lib/accessors-smart-1.2.jar  --classpath file:///home/hadoop/lib/akka-actor_2.11-2.4.20.jar 
	@Override
	public void run(String... args) throws Exception {
		
		

	}
	


}
