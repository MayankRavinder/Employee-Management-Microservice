package com.nc.spring.boot.mongo.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(NON_NULL)
public class EmployeeResponse {
	private UUID id;
	private String mobile;
	private String name;
	private Address address;

}
