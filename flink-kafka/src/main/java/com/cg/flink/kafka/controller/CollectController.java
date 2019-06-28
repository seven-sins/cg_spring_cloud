package com.cg.flink.kafka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.common.config.kafka.KafkaProducerService;
import com.cg.utils.response.Result;

/**
 * 消息发送
 * @author seven sins
 * 2019年6月29日 上午1:14:36
 */
@RestController
public class CollectController {

	@Autowired
	KafkaProducerService kafkaProducerService;
	
	@PostMapping("/producer/send")
	public Result<?> send(@RequestBody Map<String, String> map){
		kafkaProducerService.send("testTopic", "key", "123");
		
		return Result.SUCCESS;
	}
}
