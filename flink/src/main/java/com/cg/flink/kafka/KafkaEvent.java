
package com.cg.flink.kafka;

import com.alibaba.fastjson.JSONObject;

public class KafkaEvent {
	private String word;
	private int frequency;
	private long timestamp;

	public KafkaEvent() {
	}

	public KafkaEvent(String word, int frequency, long timestamp) {
		this.word = word;
		this.frequency = frequency;
		this.timestamp = timestamp;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static KafkaEvent fromString(String eventStr) {
		return JSONObject.parseObject(eventStr, KafkaEvent.class);
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
