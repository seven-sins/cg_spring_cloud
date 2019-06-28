package com.cg.common.utils;

import com.cg.common.exception.CustomException;

/**
 * 断言
 * @author Rex.Tan
 * 2019年3月26日 下午4:31:07
 */
public class Assert {

	private Assert() {

	}

	/**
	 * 断言表达式结果为是，否则抛出异常
	 * 
	 * @param expression boolean表达式
	 * @param message    异常消息
	 * @throws CustomException
	 */
	public static void isTrue(boolean expression, String msg) throws CustomException {
		if (!expression) {
			throw new CustomException(msg);
		}
	}

	/**
	 * 断言对象不空，否则抛出异常
	 * 
	 * @param object  目标对象
	 * @param message 异常消息
	 * @throws CustomException
	 */
	public static void notNull(Object object, String msg) throws CustomException {
		if (object == null) {
			throw new CustomException(msg);
		}
	}

	/**
	 * 断言对象为空，否则抛出异常
	 * 
	 * @param object  目标对象
	 * @param message 异常消息
	 * @throws CustomException
	 */
	public static void isNull(Object object, String msg) throws CustomException {
		if (object != null) {
			throw new CustomException(msg);
		}
	}

	/**
	 * 断言字符串对象和内容不为空，否则抛出异常
	 * 
	 * @param str     字符串对象
	 * @param message 异常消息
	 * @throws CustomException
	 */
	public static void notEmpty(String str, String msg) throws CustomException {
		if (str == null || "".equals(str.trim())) {
			throw new CustomException(msg);
		}
	}
}
