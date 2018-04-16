package com.nc.spring.boot.mongo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

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
		// Entity Map Converion Example
		Employee e = new Employee(1, "Mayank", null, 786.6d);
		convertEntityToEntity(e);
	}

	public static void convertEntityToEntity(Employee employee) {
		if (Objects.nonNull(employee)) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_EMPTY); // If any field value in employee is null then it
																		// would not put in MAP.
			EntityReference entityReference = new EntityReference();
			entityReference = objectMapper.convertValue(employee, EntityReference.class);
			System.out.println("Entity Reference After Conversion:" + entityReference);
		}
	}

};

class Employee {

	public int id;
	public String name;
	public String address;
	public double salary;

	public Employee(int id, String name, String address, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.salary = salary;
	}
}

@Data
class EntityReference {

	public Map<String, Object> map;

	public EntityReference() {
		map = new HashMap<>();
	}

	@JsonAnySetter
	public void setAny(final String key, final Object value) {
		map.put(key, value);
	}

}