package com.nc.spring.boot.mongo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.nc.spring.boot.mongo.adapter.EmployeeAdapter;
import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeResponseBody;
import com.nc.spring.boot.mongo.model.UpdateEmployeeRequestBody;
import com.nc.spring.boot.mongo.repositories.EmployeeRepository;

@Service("empService")
@EnableMongoRepositories({ "com.nc.spring.boot.mongo.repositories" })
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	public Employee add(Employee employee) {
		return empRepo.save(employee);
	}

	public void delete(Employee employee) {
		empRepo.delete(employee);
	}

	public void deleteAll() {
		empRepo.deleteAll();

	}

	@CachePut(value = "emp_list_cache")
	public List<Employee> findAll() {
		System.out.println("FIND ALL CALLLED");
		if (empRepo.findAll() != null)
			return empRepo.findAll();
		else
			return null;
	}

	public Employee find(Employee employee) {
		return empRepo.findOne(employee.getId());
	}

	public List<Employee> findByName(String name) {
		return empRepo.findByName(name);
	}

	public Employee findById(UUID id) {
		return empRepo.findOne(id);
	}

	public List<Employee> FindByIdGreaterThan(UUID id) {
		return empRepo.findByIdGreaterThan(id);
	}

	public List<Employee> FindByIdLessThan(UUID id) {
		return empRepo.findByIdLessThan(id);
	}

	public List<Employee> findByIdBetween(UUID from, UUID to) {
		return empRepo.findByIdBetween(from, to);
	}

	public int countNumberOfEmployees() {
		return (int) empRepo.count();
	}

	public List<Employee> getAllEmployeeSortedByIdAsc() {
		return empRepo.findByOrderByIdAsc();
	}

	public List<Employee> getAllEmployeeSortedByIdDesc() {
		return empRepo.findByOrderByIdDesc();
	}

	public List<Employee> getAllEmployeeSortedByNameAsc() {
		return empRepo.findByOrderByNameAsc();
	}

	public List<Employee> getAllEmployeeSortedByNameDesc() {
		return empRepo.findByOrderByNameDesc();
	}

	public List<Employee> getTop3EmployeeByName() {
		return empRepo.findTop3ByOrderByName();
	}

	public List<Employee> getTop3EmployeeById() {
		return empRepo.findTop3ByOrderById();
	}

	public List<Employee> getDistinctByName() {
		return empRepo.findDistinctByName("mayank lohriya");
	}

	public List<Employee> getAllEmployeeBasedUponGivenId(Iterable<UUID> iteratorOfEmployeeId) {
		Iterator<Employee> itr = empRepo.findAll(iteratorOfEmployeeId).iterator();
		List<Employee> emp_list = new ArrayList<>();
		while (itr.hasNext()) {
			emp_list.add(((Employee) itr.next()));
		}
		return emp_list;
	}

	public Employee check(UUID id) {
		System.out.println("Check called with id:" + id);
		return empRepo.findNameById(id);
	}

	public EmployeeResponseBody updateEmployee(Employee employee, UpdateEmployeeRequestBody updateRequest) {
		if (updateRequest.getUpdates().size() == 0)
			return EmployeeAdapter.convert(employee);
		updateRequest.getUpdates().values().forEach(update -> update.accept(employee));
		Employee e = empRepo.save(employee);
		return EmployeeAdapter.convert(e);
	}

}
