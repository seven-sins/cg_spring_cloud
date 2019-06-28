package com.cg.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.common.config.redis.impl.RedisServiceImpl;

/**
 * redis公共方法
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableRedis.RedisImportSelector.class })
public @interface EnableRedis {

	static class RedisImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { RedisServiceImpl.class.getName() };
		}
	}
}