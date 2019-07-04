package com.cg.common.config.date.converter;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期格式转换
 * @author Rex.Tan
 * 2019年6月20日 下午5:12:34
 */
public class CustomDateFormat extends DateFormat {

	private static final long serialVersionUID = 1L;

	private DateFormat dateFormat;
	
	public CustomDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Override
	public Date parse(String source, ParsePosition pos) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		SimpleDateFormat sdf = getSimpleDateFormat(source);
		if(sdf == null) {
			return null;
		}
		
		return sdf.parse(source, pos);
	}
	
	/**
	 * 接收参数转换
	 */
	@Override
	public Date parse(String source) throws ParseException {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat sdf = getSimpleDateFormat(source);
		if(sdf == null) {
			return null;
		}
		
		return sdf.parse(source, pos);
	}
	
	/**
	 * 返回数据日期格式转换
	 */
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		if(date == null) {
			return null;
		}
		// 如需返回其他格式, 在属性上添加注解 @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = dateFormat.format(date);
		toAppendTo.append(dateString);
		
		return new StringBuffer(toAppendTo);
	}

	@Override
	public Object clone() {
		Object format = dateFormat.clone();
		return new CustomDateFormat((DateFormat) format);
	}
	
	private SimpleDateFormat getSimpleDateFormat(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		} else if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy/MM/dd");
		} else if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}/\\d{2}/\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyyMMdd");
		} else if (Pattern.matches("^\\d{4}\\d{2}\\d{2} \\d{2}\\d{2}\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2}$", source)) {
			sdf = new SimpleDateFormat("yyyy.MM.dd");
		} else if (Pattern.matches("^\\d{4}\\.\\d{2}\\.\\d{2} \\d{2}\\.\\d{2}\\.\\d{2}$", source)) { 
			sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
		} else {
			return null;
		}
		return sdf;
	}

}
