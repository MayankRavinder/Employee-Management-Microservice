package com.nc.spring.boot.mongo.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection="employees")
@Component
@ApiModel("employeeModel")
@JsonInclude(NON_NULL)
public class Employee {

		@Id
		@ApiModelProperty("Id of Employee")
		private String id;
	
		@ApiModelProperty("Name of Employee")
		private  String name;
		
		private Address address;
	
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
		
		
		@Override
		public String toString() {
			return "Employee [id=" + id + ", name=" + name + "]";
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		
		
}
