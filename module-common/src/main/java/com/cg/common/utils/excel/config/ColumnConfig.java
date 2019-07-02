package com.cg.common.utils.excel.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 列配置信息
 */
public class ColumnConfig {
	
	/**
	 * 列标题
	 */
	private String title;
	/**
	 * 映射字段
	 */
	private String fieldName;
	/**
	 * 单元格格式
	 */
	private Format format = Format.TEXT;
	/**
	 * 列宽
	 */
	private int width = 6000;
	
	/**
	 * 自动换行
	 */
	private boolean wrapText = false;
	
	/**
	 * 数据格式
	 */
	private String dateFormat = "yyyy-MM-dd";
	
	/**
	 * 枚举值
	 */
	private Map<String,String> enums = new HashMap<>();
	
	public ColumnConfig(String title, String fieldName) {
		super();
		this.title = title;
		this.fieldName = fieldName;
	}
	
	public ColumnConfig(String title, String fieldName, Format format) {
		super();
		this.format = format;
		this.title = title;
		this.fieldName = fieldName;
	}
	
	public ColumnConfig(String title, String fieldName, Format format, String dateFormat) {
		super();
		this.format = format;
		this.title = title;
		this.fieldName = fieldName;
		this.dateFormat = dateFormat;
	}
	
	/*********************** get and shet *****************************/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public boolean isWrapText() {
		return wrapText;
	}

	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Map<String, String> getEnums() {
		return enums;
	}
	public void setEnums(Map<String, String> enums) {
		this.enums = enums;
	}
	
	/************************** ext *******************************/
	/**
	 * 设置单元格格式，默认为Format.TEXT
	 * @param format
	 * @return
	 */
	public ColumnConfig format(Format format){
		this.format = format;
		return this;
	}

	/**
	 * 设置宽度,默认6000
	 * @param width
	 * @return
	 */
	public ColumnConfig width(int width){
		this.width = width;
		return this;
	}
	
	/**
	 * 设置自动换行
	 * @param wrapText
	 * @return
	 */
	public ColumnConfig wrapText(boolean wrapText){
		this.wrapText = wrapText;
		return this;
	}
	/**
	 * 设置日期时间格式，当format=Format.DATE时有效
	 * @param dateFormat
	 * @return
	 */
	public ColumnConfig dateFormat(String dateFormat){
		this.dateFormat = dateFormat;
		return this;
	}
	/**
	 * 追加枚举值，当format=Format.TEXT时有效
	 * @param key
	 * @param value
	 * @return
	 */
	public ColumnConfig addEnum(String key,String value) {
		this.enums.put(key ,value);
		return this;
	}

}
