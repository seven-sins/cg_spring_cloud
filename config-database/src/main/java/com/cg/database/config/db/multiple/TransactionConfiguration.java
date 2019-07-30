package com.cg.database.config.db.multiple;

import java.util.Properties;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
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
	private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.cg.*.service.impl.*.*(..))";
	@Autowired
	AbstractRoutingDataSource roundRobinDataSouceProxy;
	
	@Bean
    public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(roundRobinDataSouceProxy);
    }
	
	/**
	 * 事务配置
	 * 
	 * @param transactionManager
	 * @return
	 */
	@Autowired
	@Bean
	public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(platformTransactionManager);
		Properties properties = new Properties();
		// 只读
		properties.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("query*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("select*", "PROPAGATION_REQUIRED, readOnly");
		// 可读可写
		properties.setProperty("insert*", "PROPAGATION_REQUIRED");
		properties.setProperty("delete*", "PROPAGATION_REQUIRED");
		properties.setProperty("update*", "PROPAGATION_REQUIRED");
		properties.setProperty("remove*", "PROPAGATION_REQUIRED");
		properties.setProperty("add*", "PROPAGATION_REQUIRED");
		properties.setProperty("handle*", "PROPAGATION_REQUIRED");
		properties.setProperty("**", "PROPAGATION_REQUIRED");
		transactionInterceptor.setTransactionAttributes(properties);

		return transactionInterceptor;
	}
	
	/**
	 * 配置自动代理后无法解决事务先于切换数据源AOP执行的问题, 改用AOP方式
	 */
	//	@Bean
	//	public BeanNameAutoProxyCreator transactionAutoProxy() {
	//		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
	//		transactionAutoProxy.setProxyTargetClass(true);
	//		transactionAutoProxy.setBeanNames(new String[] { "*ServiceImpl" });
	//		transactionAutoProxy.setInterceptorNames(new String[] { "transactionInterceptor" });
	//		transactionAutoProxy.setOrder(99);
	//
	//		return transactionAutoProxy;
	//	}
	
	@Autowired
	@Bean
	public Advisor txAdviceAdvisor(TransactionInterceptor transactionInterceptor) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		// 声明和设置需要拦截的方法
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		// 设置切面 = 切点pointcut + 通知TxAdvice
		return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
	}
}
