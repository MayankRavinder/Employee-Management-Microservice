package com.nc.spring.boot.mongo.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Document(collection = "employees")
@ApiModel("employeeModel")
@JsonInclude(NON_NULL)
@Data
public class Employee {

	@ApiModelProperty("Id of Employee")
	private UUID id;

	@ApiModelProperty("Name of Employee")
	private String name;

	private String mobile;

	private Address address;

	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", mobile=" + mobile + ", address=" + address + "]";
	}

}
