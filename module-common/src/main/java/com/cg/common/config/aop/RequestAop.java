package com.cg.common.config.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cg.common.exception.CustomException;

/**
 * 拦截输出异常堆栈
 * @author Rex.Tan
 * 2019年6月19日 下午4:36:56
 */
@Aspect
@Component
@Order(1)
public class RequestAop {
	static final Logger LOG = LoggerFactory.getLogger(RequestAop.class);
	
	@Pointcut("execution(* com.cg..*.*Controller.*(..))")
	public void init() {
		
	}
	
	@Before("init()")
	public void beforeAdvice(JoinPoint joinPoint) {
		// 进入方法前拦截
		printUrl(false);
	}
	
	@AfterThrowing(pointcut = "init()", throwing = "e")  
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {  
		// 主动抛出的异常
        if(e instanceof CustomException){
        	printUrl(true);
        	LOG.error("=============请求触发异常: " + ((CustomException)e).getMsg());
        }else{ 
        	// 未捕获异常
        	printUrl(true);
        	LOG.error("=============未捕获异常: ", e);
        }
	}
	
	private void printUrl(boolean isError) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		/**
		 * 输出url
		 */
		String url = request.getRequestURI();
		/**
		 * 输出查询参数
		 */
	    Enumeration<String> enu = request.getParameterNames();
	    String params = "";
		while(enu.hasMoreElements()) {
			try {
				String paraName = (String) enu.nextElement();
				params += ",  " + paraName + ": " + request.getParameter(paraName);
			} catch(Exception e) {
				LOG.error("=============输出查询参数出错", e);
				break;
			}
		}
		
		if (StringUtils.isNotBlank(url) && isError) {
			LOG.error("=============request url: " + url);
			if(!"".equals(params)) {
				params = params.replaceFirst("^\\,", "");
				LOG.error("=============请求参数: { " + params + " }");
			}
		} else {
			LOG.info("=============request url: " + url);
			if(!"".equals(params)) {
				params = params.replaceFirst("^\\,", "");
				LOG.info("=============请求参数: {" +  params + " }");
			}
		}
	}

}
