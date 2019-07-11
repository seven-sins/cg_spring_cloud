package com.cg.database.config.db.multiple;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 多数据源配置
 * @author Rex.Tan
 * 2019年7月11日 上午9:51:13
 */
@Configuration
public class DataSourceMultipleConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(DataSourceMultipleConfiguration.class);  

    @Value("${mysql.datasource.type}")  
    private Class<? extends DataSource> dataSourceType;  

    /** 
     * 写库 数据源配置 
     * @return 
     */  
    @Bean(name = "master")  
    @Primary  
    @ConfigurationProperties(prefix = "mysql.datasource.master")  
    public DataSource writeDataSource() {  
    	LOG.info("============= master DataSource init =============");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }  

    /** 
     * 有多少个从库就要配置多少个 
     * @return 
     */  
    @Bean(name = "slave01")  
    @ConfigurationProperties(prefix = "mysql.datasource.slave01")  
    public DataSource slaveDataSource01() {  
    	LOG.info("============= slave01 DataSourceOne init =============");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }

    @Bean(name = "slave02")  
    @ConfigurationProperties(prefix = "mysql.datasource.slave02")  
    public DataSource slaveDataSource02() {  
    	LOG.info("============= slave02 DataSourceOne init =============");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }

    @Bean("slaveDataSources")
    public List<DataSource> slaveDataSources(){
        List<DataSource> dataSources=new ArrayList<>();
        dataSources.add(slaveDataSource01());
        dataSources.add(slaveDataSource02());
        return dataSources;
    }

}
