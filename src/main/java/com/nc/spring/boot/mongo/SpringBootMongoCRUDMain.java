package com.nc.spring.boot.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "com.nc.spring.boot.mongo.controller", "com.nc.spring.boot.mongo.model",
		"com.nc.spring.boot.mongo.repositories", "com.nc.spring.boot.mongo.service",
		"com.nc.spring.boot.mongo.SwaggerDocument" })
@EnableCaching
@EnableAsync
@EnableZuulProxy

public class SpringBootMongoCRUDMain {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootMongoCRUDMain.class, args);
		System.out.println("Welcome to spring Boot application Running on PORT: 7777");

	}

};