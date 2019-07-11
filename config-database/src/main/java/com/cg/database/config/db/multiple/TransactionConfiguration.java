package com.cg.database.config.db.multiple;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 事务配置
 * @author Rex.Tan
 * 2019年7月11日 上午10:29:42
 */
@Configuration
@EnableTransactionManagement(order = 10)
@AutoConfigureAfter({ MybatisMultipleConfig.class })
public class TransactionConfiguration extends DataSourceTransactionManagerAutoConfiguration {
	@Autowired
	MyAbstractRoutingDataSource roundRobinDataSouceProxy;
	
	@Bean
    // @Autowired // DataSourceTransactionManager
    public PlatformTransactionManager transactionManager(MyAbstractRoutingDataSource roundRobinDataSouceProxy) {
        return new DataSourceTransactionManager(roundRobinDataSouceProxy);
    }
	
	/**
	 * 事务配置
	 * 
	 * @param transactionManager
	 * @return
	 */
	@Bean
	public TransactionInterceptor transactionInterceptor() {
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(transactionManager(roundRobinDataSouceProxy));
		Properties properties = new Properties();
		// properties.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
		// properties.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("insert*", "PROPAGATION_REQUIRED");
		properties.setProperty("delete*", "PROPAGATION_REQUIRED");
		properties.setProperty("update*", "PROPAGATION_REQUIRED");
		properties.setProperty("add*", "PROPAGATION_REQUIRED");
		properties.setProperty("handle*", "PROPAGATION_REQUIRED");
		properties.setProperty("**", "PROPAGATION_REQUIRED");
		transactionInterceptor.setTransactionAttributes(properties);

		return transactionInterceptor;
	}

	@Bean
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
		transactionAutoProxy.setProxyTargetClass(false);
		transactionAutoProxy.setBeanNames(new String[] { "*ServiceImpl" });
		transactionAutoProxy.setInterceptorNames(new String[] { "transactionInterceptor" });

		return transactionAutoProxy;
	}
}
