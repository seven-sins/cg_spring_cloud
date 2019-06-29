package com.cg.flink.task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.elasticsearch.config.ElasticsearchService;

@Component
public class Test1 {

	@Autowired
	ElasticsearchService elasticsearchService;
	
	@PostConstruct
	public void test() {
		elasticsearchService.add("index7");
	}
}
