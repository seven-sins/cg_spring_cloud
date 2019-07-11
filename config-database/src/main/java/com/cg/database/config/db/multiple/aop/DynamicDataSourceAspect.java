package com.cg.database.config.db.multiple.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cg.database.annotation.db.multiple.MasterDataSource;
import com.cg.database.annotation.db.multiple.SlaveDataSource;
import com.cg.database.config.db.multiple.DataSourceContextHolder;

/**
 * 数据源自动切换
 * 
 * @author Rex.Tan
 * @date 2019年7月11日 上午11:07:22
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
	/**
	 * 以下方法名开头的方法默认走从库的查询
	 */
	private static final ArrayList<String> REGEX_LIST = new ArrayList<>(
		Arrays.asList("^find", "^get", "^query", "^search")
	);

	//	@Pointcut("@annotation(com.cg.database.annotation.db.multiple.MasterDataSource)")
	//	public void point() {
	//	}

	@Pointcut("execution(public * com.cg.*.service..*.*(..))")
	public void excudeService() {
		
	}

	// @Around(value = "point() && excudeService()")
	@Around(value = "excudeService()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method targetMethod = methodSignature.getMethod();
		/**
		 * 默认走主库
		 * 约定规则通过方法名自动识别走主库或从库(find,get,query等默认走从库, 其他走主库)
		 */
		DataSourceContextHolder.clear();
		if (targetMethod.isAnnotationPresent(MasterDataSource.class)) {
			DataSourceContextHolder.setMaster();
		} else if (targetMethod.isAnnotationPresent(SlaveDataSource.class)) {
			DataSourceContextHolder.setSlave();
		} else {
			DataSourceContextHolder.setMaster();
			String methodName = targetMethod.getName();
			for(String item: REGEX_LIST) {
				if(Pattern.matches(item, methodName)) {
					DataSourceContextHolder.setSlave();
					break;
				}
			}
		}
		/**
		 * 执行方法
		 */
		Object result = pjp.proceed();
		DataSourceContextHolder.clear();
		
		return result;
	}
}
