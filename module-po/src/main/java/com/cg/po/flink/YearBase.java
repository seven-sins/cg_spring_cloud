package com.cg.po.flink;

public class YearBase {

	/**
	 * 年代
	 */
	private String yearType;
	/**
	 * 分组
	 */
	private String groupField;
	/**
	 * 数量
	 */
	private Long count;

	public String getYearType() {
		return yearType;
	}

	public void setYearType(String yearType) {
		this.yearType = yearType;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getGroupField() {
		return groupField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

}
