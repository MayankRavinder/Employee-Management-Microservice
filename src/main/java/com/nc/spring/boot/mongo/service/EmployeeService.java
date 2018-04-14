package com.nc.spring.boot.mongo.service;

import java.util.Iterator;
import java.util.List;

import com.nc.spring.boot.mongo.model.Employee;

public interface EmployeeService {
	
		public Employee create(Employee employee);
	 
	    public String update(Employee employee);
	 
	    public void delete(Employee employee);
	 
	    public void deleteAll();
	 
	    public Employee find(Employee employee);
	 
	    public List < Employee > findByName(String name);
	 
	    public List<Employee> findById(String id);
	 
	    public List < Employee > findAll();
	    
	    public List<Employee> FindByIdGreaterThan(String id);
	    
	    public List<Employee> FindByIdLessThan(String id);
	    
	    public List<Employee> findByIdBetween(String from,String to);

		public int countNumberOfEmployees();

		public List<Employee> getAllEmployeeSortedByIdAsc();
		
		public List<Employee> getAllEmployeeSortedByIdDesc();
		
		public List<Employee> getAllEmployeeSortedByNameAsc();
		
		public List<Employee> getAllEmployeeSortedByNameDesc();
		
	   public List<Employee> getTop3EmployeeByName();

	   public List<Employee> getTop3EmployeeById();

	   public List<Employee> getDistinctByName();
	   
	   public List<Employee> getAllEmployeeBasedUponGivenId(Iterable<String> itr );
	   
	   public Employee check(String id);
	    
	    
}
