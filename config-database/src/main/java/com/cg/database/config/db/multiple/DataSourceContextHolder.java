package com.cg.database.config.db.multiple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事务内读写配置
 * @author Rex.Tan
 * 2019年7月11日 上午10:00:40
 */
public class DataSourceContextHolder {
	private static Logger LOG = LoggerFactory.getLogger(DataSourceContextHolder.class);
	/**
	 * 线程本地环境
	 */
	private static final ThreadLocal<String> LOCAL = new ThreadLocal<String>();

	public static ThreadLocal<String> getLocal() {
		return LOCAL;
	}

	/**
	 * 读库
	 */
	public static void setSlave() {
		LOCAL.set(DataSourceType.SLAVE.getType());
		LOG.info("数据库切换到读库...");
	}

	/**
	 * 写库
	 */
	public static void setMaster() {
		LOCAL.set(DataSourceType.MASTER.getType());
		LOG.info("数据库切换到写库...");
	}

	public static String getReadOrWrite() {
		return LOCAL.get();
	}

	public static void clear() {
		LOCAL.remove();
	}

}
