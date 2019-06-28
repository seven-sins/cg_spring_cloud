package com.cg.common.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cg.common.exception.CustomException;

/**
 * 校验工具类
 * @author Rex.Tan
 * 2019年6月21日 上午11:28:18
 */
public class ValidationUtil {
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);
	/**
	 * 批量校验
	 * @param list 对象集合
	 * @return 校验结果集合
	 */
	public static void validList(List<?> list) {
		if(list == null || list.isEmpty()) {
			throw new CustomException("列表数据为空");
		}
		Set<String> result = validList(list, 0);
		if(result.size() > 0) {
			throw new CustomException(result.toString());
		}
	}

	/**
	 * 从集合指定位置开始批量校验
	 * @param lst 对象集合
	 * @param startNum 开始位置
	 * @return 校验结果集合
	 */
	public static Set<String> validList(List<?> lst, long startNum) {
		Set<String> cvSet = new HashSet<>();
		long i = startNum;
		for (Object o : lst) {
			i++;
			Set<ConstraintViolation<Object>> s = VALIDATOR.validate(o);
			for (ConstraintViolation<Object> c : s) {
				cvSet.add("第" + i + "条记录异常:" + c.getMessage());
			}
		}
		
		return cvSet;
	}
	
	/**
	 * 指定校验组从集合指定位置开始批量校验
	 * @param lst 对象集合
	 * @param startNum 开始位置
	 * @param groups 校验组
	 * @return 校验结果集合
	 */
	public static Set<String> validList(List<?> lst, long startNum, Class<?>... groups) {
		Set<String> cvSet = new HashSet<>();
		long i = startNum;
		for (Object o : lst) {
			StringBuilder buf = new StringBuilder();
			i++;
			Set<ConstraintViolation<Object>> s = VALIDATOR.validate(o, groups);
			for (ConstraintViolation<Object> c : s) {
				buf.append(c.getMessage() + ",");
			}
			if (buf.length() > 0) {
				cvSet.add("第" + i + "条记录异常:" + buf.toString().substring(0, buf.length() - 1));
			}
		}
		return cvSet;
	}

	/**
	 * 单个对象校验
	 * @param o 校验对象
	 * @return 校验结果
	 */
	public static <T> Set<ConstraintViolation<T>> valid(T o) {
		return VALIDATOR.validate(o);
	}
	
	/**
	 * 指定校验组单个对象校验
	 * @param o 校验对象
	 * @param groups 校验组
	 * @return 校验结果
	 */
	public static <T> Set<ConstraintViolation<T>> valid(T o, Class<?>... groups) {
		return VALIDATOR.validate(o, groups);
	}
	
	/**
	 * 2017-05-02  验证目标集合中的所有对象是否有xx属性重复
	 * @param list 目标集合
	 * @param attr 属性名称
	 * @return List<String> 当前存在重复的数据
	 * @author ltan
	 * @exception SpsoftException 获取属性值出错时抛出异常
	 */
	public static void fieldRepeat(List<?> list, String attr) {
		if (list.isEmpty()) {
			return;
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (fieldRepeat(list, attr, list.get(i))){
				JSONObject.toJSON(list.get(i));
				JSONObject jsonObj = (JSONObject) JSONObject.toJSON(list.get(i));
				buf.append("第" + i + "条数据重复, " + attr + ": " + jsonObj.getString(attr) + "\r\n");
			}
		}
		if(buf.length() > 0){
			throw new CustomException(buf.toString());
		}
	}
	
	/**
	 * 数据校验，可以单个对象校验，或批量校验。
	 * @param object 目标校验对象，当object为List对象时，为批量校验，否则为单个对象校验.
	 * @param groups 指定校验组
	 * @exception SpsoftException 校验未通过时抛出异常
	 */
	@SuppressWarnings("unchecked")
	public static void validate(Object object, Class<?>... groups) {
		StringBuilder result = new StringBuilder();
		if (object instanceof List) {
			List<Object> list = (List<Object>) object;
			for (int i = 0; i < list.size(); i++) {
				Set<ConstraintViolation<Object>> cvs = groups != null ? ValidationUtil.valid(list.get(i), groups) : ValidationUtil.valid(list.get(i));
				if (!cvs.isEmpty()) {
					StringBuilder msg = new StringBuilder();
					msg.append("第" + (i + 1) + "条验证结果:[");

					for (ConstraintViolation<Object> cv : cvs) {
						msg.append(cv.getMessage()).append(",");
					}
					msg.deleteCharAt(msg.length() - 1);
					msg.append("]");
					result.append(msg.toString()).append("\r\n");
				}
			}
		} else {
			Set<ConstraintViolation<Object>> cvs = groups != null ? ValidationUtil.valid(object, groups) : ValidationUtil.valid(object);
			if (!cvs.isEmpty()) {
				StringBuilder msg = new StringBuilder();

				for (ConstraintViolation<Object> cv : cvs) {
					msg.append(cv.getMessage()).append(",");
				}
				msg.deleteCharAt(msg.length() - 1);
				result.append(msg.toString());
			}
		}

		if (result.length() > 0) {
			throw new CustomException(result.toString());
		}
	}

	/**
	 * 2017-05-02  验证指定对象在目标集合中是否有xx属性重复
	 * @param list 目标集合
	 * @param attr 属性名称
	 * @param obj 目标对象
	 * @return true:是,false:否  (当存在属性重复时返回true，反之false)
	 * @author ltan
	 * @exception SpsoftException 获取属性值出错时抛出异常
	 */
	private static boolean fieldRepeat(List<?> list, String attr, Object obj) {
		int flag = 0;
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Class<? extends Object> clazz = object.getClass();
			// 获取该类型声明的成员
			Field[] fields = clazz.getDeclaredFields();
			// 遍历属性
			for (Field field : fields) {
				// 设置属性可访问
				field.setAccessible(true);
				if (attr.equals(field.getName())) {
					try {
						if (field.get(obj) != null && field.get(obj).equals(field.get(object))) {
							flag++;
							if (flag > 1) {
								return true;
							}
						}
					} catch (IllegalAccessException e) {
						LOG.error("检查数据重复操作时, 获取属性" + attr + "的值出错",e);
						throw new CustomException("检查数据重复操作时, 获取属性" + attr + "的值出错");
					}
					break;
				}
				// 恢复状态
				field.setAccessible(false);
			}
		}
		return flag > 1;
	}
	
}
