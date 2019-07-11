package com.cg.database.config.db.multiple;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 事务拦截在service层, 数据源切换必须在事务AOP之前执行
 * 
 * @author Rex.Tan 2019年7月11日 上午10:01:59
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAopInService implements PriorityOrdered {

	@Before("@annotation(com.cg.database.annotation.db.multiple.SlaveDataSource) ")
	public void setSlaveDataSourceType() {
		/**
		 * 如果已经开启写事务了，那之后的所有读都从写库读
		 */
		if (!DataSourceType.MASTER.getType().equals(DataSourceContextHolder.getReadOrWrite())) {
			DataSourceContextHolder.setRead();
		}
	}

	@Before("@annotation(com.cg.database.annotation.db.multiple.MasterDataSource) ")
	public void setMasterDataSourceType() {
		DataSourceContextHolder.setWrite();
	}

	@Override
	public int getOrder() {
		/**
		 * 值越小，越优先执行 要优于事务的执行 在启动类中加上@EnableTransactionManagement(order = 10)
		 */
		return 1;
	}

}
