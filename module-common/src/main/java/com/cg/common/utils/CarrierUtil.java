package com.cg.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 判断手机号运营商
 * @author seven sins 
 * 2019年6月30日 下午4:13:33
 */
public class CarrierUtil {
	/**
	 * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
	 **/
	private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";

	/**
	 * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
	 **/
	private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

	/**
	 * 中国移动号码格式验证
	 * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
	 **/
	private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

	/**
	 * 判断手机号码类型
	 * seven sins
	 * 2019年6月30日 下午4:17:13
	 * @param mobile
	 * @return 0、未知 1、移动 2、联通 3、电信
	 */
	public static int getCarrierByMobile(String mobile) {
		if(StringUtils.isBlank(mobile)) {
			return 0;
		}
		if (match(CHINA_MOBILE_PATTERN, mobile)) {
			return 1;
		}
		if (match(CHINA_UNICOM_PATTERN, mobile)) {
			return 2;
		}
		if (match(CHINA_TELECOM_PATTERN, mobile)) {
			return 3;
		}
		return 0;
	}
	
	/**
	 * 号码匹配
	 * seven sins
	 * 2019年6月30日 下午4:16:53
	 * @param regex
	 * @param mobile
	 * @return
	 */
	private static boolean match(String regex, String mobile) {
		return Pattern.matches(regex, mobile);
	}
}
