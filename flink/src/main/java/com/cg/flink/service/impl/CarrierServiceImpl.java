package com.cg.flink.service.impl;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cg.common.exception.CustomException;
import com.cg.common.utils.Assert;
import com.cg.elasticsearch.IndexConstraint;
import com.cg.elasticsearch.config.ElasticsearchUtil;
import com.cg.flink.service.CarrierService;
import com.cg.po.flink.Carrier;

/**
 * 保存运营商信息
 * @author seven sins
 * 2019年6月30日 下午12:19:25
 */
@Service
public class CarrierServiceImpl implements CarrierService {
	static final Logger LOG = LoggerFactory.getLogger(CarrierServiceImpl.class);
	@Autowired
	ElasticsearchUtil elasticsearchUtil;

	@Override
	public void add(Carrier carrier) {
		Assert.notNull(carrier, "参数不能为空");
		try {
			JSONObject json = get(carrier.getCarrier());
			TransportClient client = elasticsearchUtil.getClient();
			XContentBuilder doc;
			doc = XContentFactory.jsonBuilder().startObject();
			doc.field("info", carrier.getCarrier());
			if(json == null) {
				doc.field("count", carrier.getCount());
			} else {
				Long count = json.getLongValue("count");
				doc.field("count", carrier.getCount() + count);
			}
			doc.endObject();
			// 添加文档
			client.prepareIndex(IndexConstraint.CARRIER_TYPE, IndexConstraint.CARRIER_TYPE, carrier.getCarrier())
					.setSource(doc)
					.get();
		} catch(Exception e) {
			LOG.error("=============同步carrier到elasticsearch出错", e);
			throw new CustomException("同步carrier到elasticsearch出错, " + e.getMessage());
		}
	}
	
	@Override
	public JSONObject get(String id) {
		GetResponse response = elasticsearchUtil.getClient()
				.prepareGet(IndexConstraint.CARRIER_TYPE, IndexConstraint.CARRIER_TYPE, id)
				.execute()
				.actionGet();
		if(response == null) {
			return null;
		}
		return JSONObject.parseObject(response.getSourceAsString());
	}
	
}
