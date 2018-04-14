package com.nc.spring.boot.mongo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;

import com.nc.spring.boot.mongo.model.Employee;
public class EmployeeRepositoryImpl implements EmployeeRepositoryExtended{

	@Override
	public List<Employee> countByName() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
