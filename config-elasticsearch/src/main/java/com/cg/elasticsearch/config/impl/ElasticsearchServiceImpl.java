package com.cg.elasticsearch.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.cg.elasticsearch.config.ElasticsearchService;
import com.cg.elasticsearch.config.ElasticsearchUtil;

/**
 * @author seven sins
 * 2019年6月29日 下午11:04:24
 */
@Component
@AutoConfigureAfter(ElasticsearchUtil.class)
public class ElasticsearchServiceImpl implements ElasticsearchService {
	static final Logger LOG = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
	
	@Autowired
	ElasticsearchUtil elasticsearchUtil;

	@Override
	public void add(String index) {
		elasticsearchUtil.getClient().admin().indices().prepareCreate(index).get();
	}
	
}
