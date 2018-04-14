package com.nc.spring.boot.mongo.model;

import org.springframework.data.annotation.Id;

public class EmployeeResponse {
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
}
