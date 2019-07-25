//package com.cg.flink.service.impl;
//
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cg.common.exception.CustomException;
//import com.cg.common.utils.Assert;
//import com.cg.elasticsearch.IndexConstraint;
//import com.cg.elasticsearch.config.ElasticsearchUtil;
//import com.cg.flink.service.YearBaseService;
//import com.cg.po.flink.YearBase;
//
///**
// * 保存年代信息
// * @author seven sins
// * 2019年6月30日 下午12:19:25
// */
//@Service
//public class YearBaseServiceImpl implements YearBaseService {
//	static final Logger LOG = LoggerFactory.getLogger(YearBaseServiceImpl.class);
//	@Autowired
//	ElasticsearchUtil elasticsearchUtil;
//
//	@Override
//	public void add(YearBase yearBase) {
//		Assert.notNull(yearBase, "参数不能为空");
//		try {
//			JSONObject json = get(yearBase.getYearType());
//			TransportClient client = elasticsearchUtil.getClient();
//			XContentBuilder doc;
//			doc = XContentFactory.jsonBuilder().startObject();
//			doc.field("info", yearBase.getYearType());
//			if(json == null) {
//				doc.field("count", yearBase.getCount());
//			} else {
//				Long count = json.getLongValue("count");
//				doc.field("count", yearBase.getCount() + count);
//			}
//			doc.endObject();
//			// 添加文档
//			client.prepareIndex(IndexConstraint.YEAR_BASE, IndexConstraint.YEAR_BASE, yearBase.getYearType())
//					.setSource(doc)
//					.get();
//		} catch(Exception e) {
//			LOG.error("=============同步yearbase到elasticsearch出错", e);
//			throw new CustomException("同步yearbase到elasticsearch出错, " + e.getMessage());
//		}
//	}
//	
//	@Override
//	public JSONObject get(String id) {
//		GetResponse response = elasticsearchUtil.getClient()
//				.prepareGet(IndexConstraint.YEAR_BASE, IndexConstraint.YEAR_BASE, id)
//				.execute()
//				.actionGet();
//		if(response == null) {
//			return null;
//		}
//		return JSONObject.parseObject(response.getSourceAsString());
//	}
//	
//	
//	
//}
