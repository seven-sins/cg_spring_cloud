package com.cg.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.common.exception.CustomErrorController;
import com.cg.common.exception.CustomExceptionHandler;

/**
 * 全局异常捕获
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableExceptionHandler.ExceptionHandlerImportSelector.class })
public @interface EnableExceptionHandler {

	static class ExceptionHandlerImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { CustomExceptionHandler.class.getName(), CustomErrorController.class.getName() };
		}
	}
}