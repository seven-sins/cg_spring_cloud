package com.cg.elasticsearch.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * elasticsearch工具类
 * @author seven sins
 * 2019年6月29日 下午10:38:17
 */
@Component
public class ElasticsearchUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchUtil.class);
	@Value("${es.ip}")
	private String esIp;
	
	private TransportClient client;
	
	@SuppressWarnings("resource")
	public TransportClient getClient() {
		if(client == null) {
			LOG.info("=============ESIP: " + esIp);
			String[] ip = esIp.split(",");
			// 设置集群名称
	        Settings settings = Settings.builder().put("cluster.name", "my-es").build();
	        // 创建client
	        try {
	        	// client = new PreBuiltTransportClient(settings).addTransportAddresses(new TransportAddress(InetAddress.getByName(ip[0]), 9300), new TransportAddress(InetAddress.getByName(ip[1]), 9300));
	        	client = new PreBuiltTransportClient(settings).addTransportAddresses(new TransportAddress(InetAddress.getByName(ip[0]), 9300));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} 
		
        return client;
	}
}
