package com.cg.database.config.db.multiple;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源切换
 * @author Rex.Tan
 * 2019年7月11日 上午10:28:01
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

	private final int dataSourceNumber;
	private AtomicInteger count = new AtomicInteger(0);

	public MyAbstractRoutingDataSource(int dataSourceNumber) {
		this.dataSourceNumber = dataSourceNumber;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		String typeKey = DataSourceContextHolder.getReadOrWrite();
		if (StringUtils.isBlank(typeKey) || typeKey.equals(DataSourceType.MASTER.getType())) {
			return DataSourceType.MASTER.getType();
		}
		// 读 简单负载均衡
		int number = count.getAndAdd(1);
		int lookupKey = number % dataSourceNumber;
		return new Integer(lookupKey);
	}

}
