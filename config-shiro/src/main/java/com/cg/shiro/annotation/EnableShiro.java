package com.cg.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.common.annotation.EnableExceptionHandler;
import com.cg.common.annotation.EnableRedis;
import com.cg.common.annotation.EnableSpringUtil;
import com.cg.shiro.config.ShiroConfiguration;
import com.cg.shiro.config.controller.ShiroController;

/**
 * 引入shiro配置
 * @author Rex.Tan
 * 2019年3月26日 上午11:22:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
//引入异常处理
@EnableExceptionHandler
// 引入spring工具类
@EnableSpringUtil
// 引入redis工具类
@EnableRedis
@Import({ EnableShiro.ShiroWebConfigImportSelector.class })
public @interface EnableShiro {

	static class ShiroWebConfigImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			
			return new String[] { ShiroConfiguration.class.getName(), ShiroController.class.getName() };
		}
	}
}
