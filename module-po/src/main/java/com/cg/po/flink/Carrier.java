package com.cg.po.flink;

import java.io.Serializable;

/**
 * 运营商
 * 
 * @author seven sins 
 * 2019年6月30日 下午4:21:49
 */
public class Carrier implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 运营商
	 */
	private String carrier;
	/**
	 * 数量
	 */
	private Long count;
	/**
	 * 分组
	 */
	private String groupField;

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
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
