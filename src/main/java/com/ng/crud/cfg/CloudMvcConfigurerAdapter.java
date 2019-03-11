package com.ng.crud.cfg;

import java.nio.charset.Charset;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; = deprecated -> use WebMvcConfigurer

import com.ng.crud.cloud.HttpHeaderForwarderClientHttpRequestInterceptor;
import com.ng.crud.cloud.HttpHeaderForwarderHandlerInterceptor;

@Configuration
public class CloudMvcConfigurerAdapter implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer
{
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new HttpHeaderForwarderHandlerInterceptor());
	}

	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		restTemplate.setInterceptors(Collections.singletonList(new HttpHeaderForwarderClientHttpRequestInterceptor()));
		return restTemplate;
	}
}
