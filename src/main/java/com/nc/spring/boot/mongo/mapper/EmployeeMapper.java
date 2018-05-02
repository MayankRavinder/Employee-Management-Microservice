package com.nc.spring.boot.mongo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeRequestBody;
import com.nc.spring.boot.mongo.model.EmployeeResponse;

@Mapper
public abstract class EmployeeMapper {

	public static final EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	public abstract EmployeeResponse map(Employee employee);

	@Mappings({ @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())") })
	public abstract Employee map(EmployeeRequestBody employeeRequest);
}
