package com.ng.crud;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;// deprecated -> WebMvcConfigurationSupport 

import com.ng.crud.cloud.HttpHeaderForwarderHandlerInterceptor;

@SuppressWarnings("deprecation")
@SpringBootApplication
@ComponentScan({"com.ng.crud.controller", "com.ng.crud.repo", "com.ng.crud.cfg"})
public class ExpoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ExpoApplication.class, args);
	}
}
