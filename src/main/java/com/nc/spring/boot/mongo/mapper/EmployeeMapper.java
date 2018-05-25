package com.nc.spring.boot.mongo.mapper;


import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeRequestBody;
import com.nc.spring.boot.mongo.model.EmployeeResponseBody;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "employees")
public abstract class EmployeeMapper {

	public static final EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	public abstract EmployeeResponseBody map(Employee employee);

	@Mappings({@Mapping(target="id",expression="java(java.util.UUID.randomUUID())")} )

	public abstract Employee map(EmployeeRequestBody employeeRequest);
}
