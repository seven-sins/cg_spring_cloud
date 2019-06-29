package com.cg.elasticsearch.config;

/**
 * @author seven sins
 * 2019年6月29日 下午11:03:32
 */
public interface ElasticsearchService {
	
	/**
	 * 创建索引
	 * @param index
	 */
	void add(String index);
}
