package com.cg.po.flink;

import java.util.List;

import com.cg.po.business.OrderInfo;

/**
 * 败家指数
 * 
 * @author seven sins 
 * 2019年6月30日 下午5:23:28
 */
public class Prodigal extends OrderInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 败家指数(0-20, 20-50, 50-70, 70-80, 80-90, 90-100
	 */
	private String prodigalType;

	private Long count;

	private String groupField;

	private List<Prodigal> list;

	public String getProdigalType() {
		return prodigalType;
	}

	public void setProdigalType(String prodigalType) {
		this.prodigalType = prodigalType;
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

	public List<Prodigal> getList() {
		return list;
	}

	public void setList(List<Prodigal> list) {
		this.list = list;
	}

}
