package com.cg.flink;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableMvnConfig;
import com.cg.common.annotation.EnableSpringUtil;
import com.cg.flink.stream.task.UserVisitTask;

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
		SpringApplication.run(FlinkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserVisitTask.main(args);
	}
}
