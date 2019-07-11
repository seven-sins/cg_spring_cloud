package com.cg.database.config.db.multiple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.github.pagehelper.PageInterceptor;

/**
 * mybatis配置
 * @author Rex.Tan
 * 2019年7月11日 上午10:14:03
 */
@Configuration
@AutoConfigureAfter(DataSourceMultipleConfiguration.class)
public class MybatisMultipleConfig {
	static Logger LOG = LoggerFactory.getLogger(MybatisMultipleConfig.class);

	@Value("${mysql.datasource.readSize}")
	private String readDataSourceSize;
	@Autowired
	@Qualifier("masterDataSource")
	private DataSource masterDataSource;
	@Autowired
	@Qualifier("slaveDataSources")
	private List<DataSource> slaveDataSources;
	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;
	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(roundRobinDataSouceProxy());
		bean.setTypeAliasesPackage(typeAliasesPackage);
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(mapperLocations));
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		bean.getObject().getConfiguration().setAutoMappingBehavior(AutoMappingBehavior.FULL);

		// 分页插件
		PageInterceptor pageHelper = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("helperDialect", "mysql");
		properties.setProperty("reasonable", "false");
		properties.setProperty("authRuntimeDialect", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);

		// 添加插件
		bean.setPlugins(new Interceptor[] { pageHelper });
		
		return bean.getObject();
	}

	/**
	 * 有多少个数据源就要配置多少个bean
	 * 
	 * @return
	 */
	@Bean
	public AbstractRoutingDataSource roundRobinDataSouceProxy() {
		int size = Integer.parseInt(readDataSourceSize);
		MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
		// 写
		targetDataSources.put(DataSourceType.MASTER.getType(), masterDataSource);
		// targetDataSources.put(DataSourceType.read.getType(),readDataSource);
		// 多个读数据库时
		for (int i = 0; i < size; i++) {
			targetDataSources.put(i, slaveDataSources.get(i));
		}
		proxy.setDefaultTargetDataSource(masterDataSource);
		proxy.setTargetDataSources(targetDataSources);
		
		return proxy;
	}

}
