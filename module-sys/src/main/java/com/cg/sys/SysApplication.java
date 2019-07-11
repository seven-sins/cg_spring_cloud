package com.cg.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.common.annotation.EnableMvnConfig;
import com.cg.database.annotation.db.single.EnableDatabase;
import com.cg.shiro.annotation.EnableShiro;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统服务
 * @author Rex.Tan
 * 2019年3月25日 下午2:03:44
 */
/**
 * 启用shiro配置
 */
@EnableShiro
/**
 * 启用数据库配置
 */
@EnableDatabase
/**
 * 启用mvc配置
 */
@EnableMvnConfig
/**
 * 扫描mapper
 */
@MapperScan("com.cg.mapper")
@SpringBootApplication
public class SysApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}
	
}
