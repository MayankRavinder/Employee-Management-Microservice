package com.nc.spring.boot.mongo.adapter;
import org.springframework.stereotype.Component;

import com.nc.spring.boot.mongo.mapper.EmployeeMapper;
import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeRequestBody;
import com.nc.spring.boot.mongo.model.EmployeeResponseBody;

@Component
public class EmployeeAdapter {

	public static final EmployeeMapper mapper = EmployeeMapper.INSTANCE;

	public static EmployeeResponseBody convert(Employee e) {
		return mapper.map(e);
	}

	public static Employee convert(EmployeeRequestBody request) {
		System.out.println("Trying to convert request to entity");
		return mapper.map(request);
	}
}
