package com.nc.spring.boot.mongo.model;

import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeRequestBody {

	private UUID id;
	private String name;
	private String mobile;
	private Address address;

	@Override
	public String toString() {
		return "EmployeeRequestBody [id=" + id + ", name=" + name + ", mobile=" + mobile + ", address=" + address + "]";
	}

}
