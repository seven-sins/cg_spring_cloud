package com.cg.utils.request;

/**
 * 页面参数
 * 
 * @author Rex.Tan 2019年6月21日 上午11:40:28
 */
public class Page {
	/**
	 * 页码
	 */
	private Integer index = 1;
	/**
	 * 每页数量
	 */
	private Integer size = 20;
	/**
	 * 排序
	 */
	private String orderBy = "";

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
