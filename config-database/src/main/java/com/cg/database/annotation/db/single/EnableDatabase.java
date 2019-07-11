package com.cg.database.annotation.db.single;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.cg.database.config.db.single.DruidConfiguration;
import com.cg.database.config.db.single.MyBatisConfig;

/**
 * 引入数据库配置
 * @author Rex.Tan
 * 2019年3月25日 下午2:36:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Inherited
@Import({ EnableDatabase.DatabaseImportSelector.class })
public @interface EnableDatabase {

	static class DatabaseImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			return new String[] { DruidConfiguration.class.getName(), MyBatisConfig.class.getName() };
		}
	}
}