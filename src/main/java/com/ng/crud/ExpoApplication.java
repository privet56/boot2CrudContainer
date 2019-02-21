package com.ng.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({"com.ng.crud.controller", "com.ng.crud.repo", "com.ng.crud.cfg"})
public class ExpoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ExpoApplication.class, args);
	}
}
