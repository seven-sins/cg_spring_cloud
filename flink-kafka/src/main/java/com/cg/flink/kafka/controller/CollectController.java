package com.cg.flink.kafka.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cg.po.bigdata.Message;
import com.cg.utils.response.Result;

/**
 * 消息发送
 * @author seven sins
 * 2019年6月29日 上午1:14:36
 */
@RestController
public class CollectController {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@PostMapping("/producer/send")
	public Result<?> send(@RequestBody JSONObject json){
		// 获取消息, 将获取到的消息封装到message
		Message msg = new Message();
		msg.setMessage(json.toJSONString());
		msg.setCount(1);
		msg.setTimestamp(new Date().getTime());
		
		String jsonString = JSON.toJSONString(msg);
		// 业务开始
		kafkaTemplate.send("test2", "key", jsonString);
		
		return Result.SUCCESS;
	}
}
