package com.cg.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.elasticsearch.config.ElasticsearchUtil;
import com.cg.elasticsearch.config.impl.ElasticsearchServiceImpl;

/**
 * 引入数据库配置
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableElasticsearch.ElasticsearchImportSelector.class })
public @interface EnableElasticsearch {

	static class ElasticsearchImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { ElasticsearchUtil.class.getName(), ElasticsearchServiceImpl.class.getName() };
		}
	}
}