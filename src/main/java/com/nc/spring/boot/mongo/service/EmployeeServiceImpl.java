package com.nc.spring.boot.mongo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.repositories.EmployeeRepository;

@Service("empService")
@EnableMongoRepositories({ "com.nc.spring.boot.mongo.repositories" })
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepo;
	
	
	@Override
	public Employee create(Employee employee) {
		return empRepo.insert(employee);
	}

	@Override
	public String update(Employee employee) {
		Employee e=null;
		e=(Employee)empRepo.findOne(employee.getId());
		if(e!=null){
			empRepo.save(employee);
			return "Updated Successfully";
		}
		return "Not Updated Successfully";
		
	}

	@Override
	public void delete(Employee employee) {
      empRepo.delete(employee);
	}

	@Override
	public void deleteAll() {
		empRepo.deleteAll();
		
	}

	@Override
	@CachePut(value="emp_list_cache")
	public List<Employee> findAll() {
		System.out.println("FIND ALL CALLLED");
		if(empRepo.findAll()!=null)
		return empRepo.findAll();
		else
		return null;
	}
	
	@Override
	public Employee find(Employee employee) {
		return empRepo.findOne(employee.getId());
	}

	@Override
	public List<Employee> findByName(String name) {
	     return  empRepo.findByName(name);
	}

	@Override
	public List<Employee> findById(String id) {
		return empRepo.findById(id);
	}

	@Override
	public List<Employee> FindByIdGreaterThan(String id) {
		return empRepo.findByIdGreaterThan(id);
	}

	@Override
	public List<Employee> FindByIdLessThan(String id) {
		return empRepo.findByIdLessThan(id);
	}

	@Override
	public List<Employee> findByIdBetween(String from, String to) {
		return empRepo.findByIdBetween(from, to);
	}

	@Override
	public int countNumberOfEmployees() {
		return (int) empRepo.count();
	}
	@Override
	public List<Employee> getAllEmployeeSortedByIdAsc(){
		return empRepo.findByOrderByIdAsc();
	}
	
	@Override
	public List<Employee> getAllEmployeeSortedByIdDesc(){
		return empRepo.findByOrderByIdDesc();
	}

	@Override
	public List<Employee> getAllEmployeeSortedByNameAsc() {
		return empRepo.findByOrderByNameAsc();
	}

	@Override
	public List<Employee> getAllEmployeeSortedByNameDesc() {
		return empRepo.findByOrderByNameDesc();
	}

	@Override
	public List<Employee> getTop3EmployeeByName() {
		return empRepo.findTop3ByOrderByName();
	}

	@Override
	public List<Employee> getTop3EmployeeById() {
		return empRepo.findTop3ByOrderById();
	}

	@Override
	public List<Employee> getDistinctByName() {
		return empRepo.findDistinctByName("mayank lohriya");
	}

	@Override
	public List<Employee> getAllEmployeeBasedUponGivenId(Iterable<String> iteratorOfEmployeeId) {
	         Iterator<Employee> itr=empRepo.findAll(iteratorOfEmployeeId).iterator();
	         List<Employee> emp_list=new ArrayList<>();
	         while(itr.hasNext()) {
	        	 emp_list.add(((Employee)itr.next()));
	         }
	         return emp_list;
	}

	@Override
	public Employee check(String id) {
		System.out.println("Check called with id:"+id);
		return empRepo.findNameById(id);
	}
	
	

	

}
