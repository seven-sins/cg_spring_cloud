package com.cg.po.flink;

import java.io.Serializable;

/**
 * 年代
 * @author seven sins
 * 2019年6月30日 下午5:40:21
 */
public class YearBase implements Serializable {
	private static final long serialVersionUID = 1L;
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
