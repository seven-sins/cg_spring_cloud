package com.cg.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.common.config.spring.WebMvcConfig;

/**
 * webMvcConfig
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableMvnConfig.MvnConfigImportSelector.class })
public @interface EnableMvnConfig {

	static class MvnConfigImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { WebMvcConfig.class.getName() };
		}
	}
}