package com.cg.po.bigdata;

/**
 * kafka消息体
 * @author seven sins
 * @date 2019年7月29日 下午8:56:28
 */
public class Message {
	/**
	 * 消息次数
	 */
	private int count;
	/**
	 * 时间戳
	 */
	private Long timestamp;
	/**
	 * 消息体
	 */
	private String message;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
