package com.cg.common.config.spring;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.cg.common.config.date.converter.CustomDateFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * MVC配置
  * 拦截器配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    protected static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
    
    /**
 	 * 将当前登录用户自动注入
     */
	//    @Override
	//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	//		argumentResolvers.add(new UserArgumentResolver());
	//	}

	/**
	 * 为true 路径参数如果带"."， 则后面的值将被忽略 默认为true
	 */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    /**
     * 配置全局日期格式转换
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    	MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        // 请求中有多余的参数不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置全局时间转换
        DateFormat dateFormat = objectMapper.getDateFormat();
        objectMapper.setDateFormat(new CustomDateFormat(dateFormat));

        // 设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        jackson2HttpMessageConverter.setSupportedMediaTypes(list);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }
}
