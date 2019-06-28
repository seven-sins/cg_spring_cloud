package com.cg.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.common.kafka.KafkaProducerConfig;

/**
 * springUtil工具类
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableKafkaServer.KafkaServerImportSelector.class })
public @interface EnableKafkaServer {

	static class KafkaServerImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { KafkaProducerConfig.class.getName() };
		}
	}
}