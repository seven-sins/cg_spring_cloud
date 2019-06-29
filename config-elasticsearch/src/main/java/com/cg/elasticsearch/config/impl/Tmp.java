//package com.cdc.elasticsearch.service.impl;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.dubbo.common.utils.StringUtils;
//import com.alibaba.fastjson.JSONObject;
//import com.cdc.common.util.SerialNo;
//import com.cdc.elasticsearch.service.IElasticsearchService;
//import com.cdc.elasticsearch.service.utils.EsUtil;
//import com.cdc.entity.front.TopicImages;
//import com.cdc.entity.topic.CommentImagesv2;
//import com.cdc.entity.topic.Commentv2;
//import com.cdc.entity.topic.Topicv2;
//import com.cdc.result.front.Response;
//
///**
// * 
// * 因新版Elasticsearch废弃一个索引下可以创建多个mapping的设定
// * 
// * 改为一个索引对应一个mapping
// * 
// * 索引和类型一致
// * 
// * @author Tan Ling
// * 2018年10月22日 下午2:53:43
// */
//@Service
//public class ElasticsearchServiceImpl implements IElasticsearchService {
//	private static final Logger LOG = Logger.getLogger(ElasticsearchServiceImpl.class);
//	@Autowired
//	EsUtil esUtil;
//	
//	@Override
//	public Object add(String index) {
//		LOG.info("=============create index: " + index);
//		TransportClient client = esUtil.getClient();
//		client.admin().indices().prepareCreate(index).get();
//		
//		return 1;
//	}
//
//	@Override
//	public Response<?> add(Object object, String dataType, String id, String title, String content) {
//		if(object == null || StringUtils.isBlank(dataType)) {
//			return new Response<>(1, "参数不能为空");
//		}
//		if(StringUtils.isBlank(id)) {
//			id = SerialNo.getUNID();
//		}
//		LOG.info("=============dataType: " + dataType + ", id: " + id);
//		String josnString = JSONObject.toJSONString(object);
//		JSONObject jsonObject = JSONObject.parseObject(josnString);
//		
//		TransportClient client = esUtil.getClient();
//		XContentBuilder doc;
//		try {
//			doc = XContentFactory.jsonBuilder().startObject();
//			if(StringUtils.isNotEmpty(title)) {
//				doc.field("title", title);
//			}
//			if(StringUtils.isNotEmpty(content)) {
//				doc.field("content", content);
//			}
//			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
//				if(entry.getValue() != null) {
//					LOG.info("=============key: " + entry.getKey() + ", value: " + entry.getValue());
//					doc.field(entry.getKey(), entry.getValue());
//				}
//	        }
//			doc.endObject();
//		} catch (IOException e) {
//			LOG.error("=============添加数据出错: ", e);
//			return new Response<>(1, "添加数据出错");
//		}
//		// 添加文档
//		IndexResponse response = client.prepareIndex(dataType, dataType, id).setSource(doc).get();
//		LOG.info("=============status: " + response.status());
//		
//		return Response.SUCCESS;
//	}
//	
//	@Override
//	public Response<JSONObject> get(String id, String dataType){
//		if(StringUtils.isBlank(id)) {
//			return new Response<>(1, "参数不能为空");
//		}
//		TransportClient client = esUtil.getClient();
//		// 根据ID查询
//		QueryBuilder qb = QueryBuilders.idsQuery().addIds(id);
//		
//		SearchResponse response = client.prepareSearch(dataType)
//							.setTypes(dataType)
//							.setQuery(qb)
//							.get();
//		SearchHits hits = response.getHits();
//		JSONObject jsonObject = null;
//		for(SearchHit hit: hits) {
//			jsonObject = JSONObject.parseObject(hit.getSourceAsString());
//		}
//		
//		return new Response<JSONObject>().data(jsonObject);
//	}
//	
//	@Override
//	public Response<JSONObject> find(String params, int pageNumber, int pageSize){
//		int start = (pageNumber - 1) * pageNumber;
//		TransportClient client = esUtil.getClient();
//		// 
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		
//		if(!StringUtils.isBlank(params)) {
//			String[] param = params.split(",");
//			for(String item: param) {
//				if(StringUtils.isNotEmpty(item.trim())) {
//					boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("title", item.trim()));
//					boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("content", item.trim()));
//					
//					//boolQueryBuilder.should(QueryBuilders.regexpQuery("title", ".*" + item.trim() + ".*"));
//					//boolQueryBuilder.should(QueryBuilders.regexpQuery("content", ".*" + item.trim() + ".*"));
//				}
//			}
//		}
//
//		SearchResponse sr = client.prepareSearch(Topicv2.ES_TYPE, Commentv2.ES_TYPE)
//							.setQuery(boolQueryBuilder)
//							.setFrom(start)
//							.setSize(pageSize)
//							.addSort(SortBuilders.fieldSort("order").order(SortOrder.DESC))
//							.addSort(SortBuilders.fieldSort("sort").order(SortOrder.DESC))
//							.get();
//		SearchHits hits = sr.getHits();
//		List<JSONObject> list = new ArrayList<>();
//		for(SearchHit hit: hits) {
//			JSONObject jsonObj = JSONObject.parseObject(hit.getSourceAsString());
//			if(jsonObj.containsKey("esType")) {
//				list.add(jsonObj);
//			}
//		}
//		
//		Response<JSONObject> response = new Response<>();
//		if(list != null && !list.isEmpty()) {
//			response.setPageNumber(pageNumber);
//			response.setPageSize(pageSize);
//			response.setRowCount((int)hits.getTotalHits());
//			response.list(list);
//		} else {
//			LOG.info("=============使用wildcardQuery查询: " + params);
//			return wildcardQuery(params, pageNumber, pageSize);
//		}
//		
//		return response;
//	}
//	
//	@Override
//	public Response<JSONObject> findByTitle(String params, int pageNumber, int pageSize){
//		int start = (pageNumber - 1) * pageNumber;
//		if(StringUtils.isBlank(params)) {
//			return new Response<>(1, "参数不能为空");
//		}
//		String[] param = params.split(",");
//		TransportClient client = esUtil.getClient();
//		// 
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		
//		for(String item: param) {
//			if(StringUtils.isNotEmpty(item.trim())) {
//				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("title", item.trim()));
//			}
//		}
//		
//		SearchResponse sr = client.prepareSearch(Topicv2.ES_TYPE, Commentv2.ES_TYPE)
//							.setQuery(boolQueryBuilder)
//							.setFrom(start)
//							.setSize(pageSize)
//							.addSort(SortBuilders.fieldSort("order").order(SortOrder.DESC))
//							.addSort(SortBuilders.fieldSort("sort").order(SortOrder.DESC))
//							.get();
//		SearchHits hits = sr.getHits();
//		List<JSONObject> list = new ArrayList<>();
//		for(SearchHit hit: hits) {
//			JSONObject jsonObj = JSONObject.parseObject(hit.getSourceAsString());
//			list.add(jsonObj);
//		}
//		
//		Response<JSONObject> response = new Response<>();
//		if(hits != null) {
//			response.setRowCount((int)hits.getTotalHits());
//			response.list(list);
//		}
//		
//		return response;
//	}
//	
//	@Override
//	public Response<JSONObject> findByContent(String params, int pageNumber, int pageSize){
//		int start = (pageNumber - 1) * pageNumber;
//		if(StringUtils.isBlank(params)) {
//			return new Response<>(1, "参数不能为空");
//		}
//		String[] param = params.split(",");
//		TransportClient client = esUtil.getClient();
//		// 
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		
//		for(String item: param) {
//			if(StringUtils.isNotEmpty(item.trim())) {
//				boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("content", item.trim()));
//			}
//		}
//		
//		SearchResponse sr = client.prepareSearch(Topicv2.ES_TYPE, Commentv2.ES_TYPE)
//							.setQuery(boolQueryBuilder)
//							.setFrom(start)
//							.setSize(pageSize)
//							.addSort(SortBuilders.fieldSort("order").order(SortOrder.DESC))
//							.addSort(SortBuilders.fieldSort("sort").order(SortOrder.DESC))
//							.get();
//		SearchHits hits = sr.getHits();
//		List<JSONObject> list = new ArrayList<>();
//		for(SearchHit hit: hits) {
//			JSONObject jsonObj = JSONObject.parseObject(hit.getSourceAsString());
//			list.add(jsonObj);
//		}
//		
//		Response<JSONObject> response = new Response<>();
//		if(hits != null) {
//			response.setRowCount((int)hits.getTotalHits());
//			response.list(list);
//		}
//		
//		return response;
//	}
//
//	@Override
//	public Response<?> addTopic(Topicv2 topic) {
//		if(topic == null) {
//			return new Response<>(1, "参数不能为空");
//		}
//		
//		TransportClient client = esUtil.getClient();
//		XContentBuilder doc;
//		try {
//			doc = XContentFactory.jsonBuilder().startObject();
//			doc.field("title", topic.getTopicTitle());
//			doc.field("id", topic.getId());
//			doc.field("content", topic.getTopicContent());
//			doc.field("topicType", topic.getTopicType());
//			doc.field("esType", Topicv2.ES_TYPE);
//			doc.field("order", 1);
//			if(topic.getCreateTime() != null) {
//				doc.field("date", dateFormat(topic.getCreateTime()));
//				doc.field("sort", topic.getCreateTime().getTime());
//			}
//			List<JSONObject> img = new ArrayList<>();
//			if(StringUtils.isNotEmpty(topic.getTopicCover())) {
//				JSONObject obj = new JSONObject();
//				obj.put("id", topic.getId());
//				obj.put("url", topic.getTopicCover());
//				img.add(obj);
//			}
//			if(topic.getImageList() != null && !topic.getImageList().isEmpty()) {
//				for(TopicImages item: topic.getImageList()) {
//					JSONObject obj = new JSONObject();
//					obj.put("id", item.getId());
//					obj.put("url", item.getTopicImage());
//					img.add(obj);
//				}
//			}
//			doc.field("images", img);
//			
//			doc.endObject();
//		} catch (IOException e) {
//			LOG.error("=============添加数据出错: ", e);
//			return new Response<>(1, "添加数据出错");
//		}
//		// 添加文档
//		IndexResponse response = client.prepareIndex(Topicv2.ES_TYPE, Topicv2.ES_TYPE, topic.getId()).setSource(doc).get();
//		LOG.info("=============add topic, status: " + response.status());
//		
//		return Response.SUCCESS;
//	}
//
//	@Override
//	public Response<?> addComment(Commentv2 comment) {
//		if(comment == null) {
//			return new Response<>(1, "参数不能为空");
//		}
//		if(StringUtils.isBlank(comment.getContent())) {
//			return Response.SUCCESS;
//		}
//		TransportClient client = esUtil.getClient();
//		XContentBuilder doc;
//		try {
//			doc = XContentFactory.jsonBuilder().startObject();
//			doc.field("title", ""); // 打卡动态
//			doc.field("id", comment.getId());
//			doc.field("content", comment.getContent());
//			doc.field("commentType", comment.getCommentType());
//			doc.field("nickname", comment.getNickname());
//			doc.field("photo", comment.getPhoto());
//			doc.field("esType", Commentv2.ES_TYPE);
//			doc.field("order", 0);
//			if(comment.getCreateTime() != null) {
//				doc.field("date", dateFormat(comment.getCreateTime()));
//				doc.field("sort", comment.getCreateTime().getTime());
//			}
//			
//			if(comment.getImages() != null && !comment.getImages().isEmpty()) {
//				List<JSONObject> img = new ArrayList<>();
//				for(CommentImagesv2 item: comment.getImages()) {
//					JSONObject obj = new JSONObject();
//					obj.put("id", item.getId());
//					obj.put("url", item.getImageUrl());
//					img.add(obj);
//				}
//				doc.field("images", img);
//			}
//			
//			doc.endObject();
//		} catch (IOException e) {
//			LOG.error("=============添加数据出错: ", e);
//			return new Response<>(1, "添加数据出错");
//		}
//		// 添加文档
//		IndexResponse response = client.prepareIndex(Commentv2.ES_TYPE, Commentv2.ES_TYPE, comment.getId()).setSource(doc).get();
//		LOG.info("=============add comment, status: " + response.status());
//		
//		return Response.SUCCESS;
//	}
//
//	@Override
//	public Response<?> deleteTopic(String id) {
//		TransportClient client = esUtil.getClient();
//		DeleteResponse result = client.prepareDelete(Topicv2.ES_TYPE, Topicv2.ES_TYPE, id).get();
//		LOG.info("delete topic: " + id + " ,status: " + result.status());
//		return Response.SUCCESS;
//	}
//
//	@Override
//	public Response<?> deleteComment(String id) {
//		TransportClient client = esUtil.getClient();
//		DeleteResponse result = client.prepareDelete(Commentv2.ES_TYPE, Commentv2.ES_TYPE, id).get();
//		LOG.info("delete comment: " + id + " ,status: " + result.status());
//		return Response.SUCCESS;
//	}
//
//	private String dateFormat(Date date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return sdf.format(date); 
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	private Response<JSONObject> wildcardQuery(String params, int pageNumber, int pageSize){
//		int start = (pageNumber - 1) * pageNumber;
//		TransportClient client = esUtil.getClient();
//		// 
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		
//		if(!StringUtils.isBlank(params)) {
//			String[] param = params.split(",");
//			for(String item: param) {
//				if(StringUtils.isNotEmpty(item.trim())) {
//					// boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("title", item.trim()));
//					// boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("content", item.trim()));
//					
//					boolQueryBuilder.should(QueryBuilders.wildcardQuery("title", "*" + item.trim() + "*"));
//					boolQueryBuilder.should(QueryBuilders.wildcardQuery("content", "*" + item.trim() + "*"));
//				}
//			}
//		}
//		
//		SearchResponse sr = client.prepareSearch(Topicv2.ES_TYPE, Commentv2.ES_TYPE)
//							.setQuery(boolQueryBuilder)
//							.setFrom(start)
//							.setSize(pageSize)
//							.addSort("order", SortOrder.DESC)
//							.get();
//		SearchHits hits = sr.getHits();
//		List<JSONObject> list = new ArrayList<>();
//		for(SearchHit hit: hits) {
//			JSONObject jsonObj = JSONObject.parseObject(hit.getSourceAsString());
//			if(jsonObj.containsKey("esType")) {
//				list.add(jsonObj);
//			}
//		}
//		
//		Response<JSONObject> response = new Response<>();
//		if(list != null && !list.isEmpty()) {
//			response.setPageNumber(pageNumber);
//			response.setPageSize(pageSize);
//			response.setRowCount((int)hits.getTotalHits());
//			response.list(list);
//		} 
//		
//		return response;
//	}
//}
