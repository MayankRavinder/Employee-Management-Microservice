package com.nc.spring.boot.mongo.repositories;

import java.util.List;

import com.nc.spring.boot.mongo.model.Employee;

public interface EmployeeRepositoryExtended {
	List<Employee> countByName(); 
}
