package com.nc.spring.boot.mongo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nc.spring.boot.mongo.model.Employee;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, UUID>, EmployeeRepositoryExtended {
	
	Employee findById(UUID id);

	List<Employee> findByName(String name);

	List<Employee> findByIdGreaterThan(UUID id);

	List<Employee> findByIdLessThan(UUID id);

	List<Employee> findByIdBetween(UUID from, UUID to);

	List<Employee> findByOrderByIdAsc();

	List<Employee> findByOrderByIdDesc();

	List<Employee> findByOrderByNameAsc();

	List<Employee> findByOrderByNameDesc();

	List<Employee> findTop3ByOrderById();

	List<Employee> findTop3ByOrderByName();

	List<Employee> findDistinctByName(String name);

	// @Query( fields = "{'name':0 }") //Limiting response fields //If we write ,
	// then name would not be show in response.
	Employee findNameById(UUID id);

}
