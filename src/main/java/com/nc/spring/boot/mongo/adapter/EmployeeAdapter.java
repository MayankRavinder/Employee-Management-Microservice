package com.nc.spring.boot.mongo.adapter;

import org.springframework.stereotype.Component;

import com.nc.spring.boot.mongo.mapper.EmployeeMapper;
import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeRequestBody;
import com.nc.spring.boot.mongo.model.EmployeeResponse;

@Component
public class EmployeeAdapter {

	private static final EmployeeMapper mapper = EmployeeMapper.INSTANCE;

	public static EmployeeResponse convert(Employee e) {
		return mapper.map(e);
	}

	public static Employee convert(EmployeeRequestBody request) {
		System.out.println("Trying to convert request to entity");
		return mapper.map(request);
	}
}
