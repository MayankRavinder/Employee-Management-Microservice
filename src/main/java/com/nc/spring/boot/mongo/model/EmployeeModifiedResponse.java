package com.nc.spring.boot.mongo.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("hiding")
class Response<Employee> {
	public Employee getValue() {
		return value;
	}

	public void setValue(Employee value) {
		this.value = value;
	}

	private Employee value;
}

@JsonInclude(NON_NULL)
public class EmployeeModifiedResponse extends Response<Employee> {

	@Override
	public Employee getValue() {
		return super.getValue();
	}

}
