package com.cg.flink.stream.transfer;

import java.io.IOException;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import com.alibaba.fastjson.JSON;
import com.cg.po.bigdata.Message;

public class KafkaMessageSchema implements DeserializationSchema<Message>, SerializationSchema<Message> {
	private static final long serialVersionUID = 1L;

	@Override
	public Message deserialize(byte[] message) throws IOException {
		String jsonString = new String(message);
		Message kafkaMessage = JSON.parseObject(jsonString, Message.class);
		return kafkaMessage;
	}

	@Override
	public byte[] serialize(Message element) {
		String jsonstring = JSON.toJSONString(element);
		return jsonstring.getBytes();
	}

	@Override
	public boolean isEndOfStream(Message nextElement) {
		return false;
	}

	@Override
	public TypeInformation<Message> getProducedType() {
		return TypeInformation.of(Message.class);
	}
}
