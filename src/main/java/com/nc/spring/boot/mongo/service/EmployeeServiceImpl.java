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
import com.nc.spring.boot.mongo.model.EmployeeResponse;
import com.nc.spring.boot.mongo.model.UpdateEmployeeRequestBody;
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
	public void delete(Employee employee) {
		empRepo.delete(employee);
	}

	@Override
	public void deleteAll() {
		empRepo.deleteAll();

	}

	@Override
	@CachePut(value = "emp_list_cache")
	public List<Employee> findAll() {
		System.out.println("FIND ALL CALLLED");
		if (empRepo.findAll() != null)
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
		return empRepo.findByName(name);
	}

	@Override
	public List<Employee> findById(UUID id) {
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
	public List<Employee> getAllEmployeeSortedByIdAsc() {
		return empRepo.findByOrderByIdAsc();
	}

	@Override
	public List<Employee> getAllEmployeeSortedByIdDesc() {
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
	public List<Employee> getAllEmployeeBasedUponGivenId(Iterable<UUID> iteratorOfEmployeeId) {
		Iterator<Employee> itr = empRepo.findAll(iteratorOfEmployeeId).iterator();
		List<Employee> emp_list = new ArrayList<>();
		while (itr.hasNext()) {
			emp_list.add(((Employee) itr.next()));
		}
		return emp_list;
	}

	@Override
	public Employee check(String id) {
		System.out.println("Check called with id:" + id);
		return empRepo.findNameById(id);
	}

	public EmployeeResponse updateEmployee(Employee employee, UpdateEmployeeRequestBody updateRequest) {
		System.out.println("Actual Employee:" + employee);
		System.out.println("Updated Employee :" + updateRequest);
		if (updateRequest.getUpdates().size() == 0)
			return EmployeeAdapter.convert(employee);

		updateRequest.getUpdates().values().forEach(update -> update.accept(employee));

		System.out.println("Actual Employee after updation:" + employee);
		System.out.println("Updated Employee After Updation :" + updateRequest);
		Employee e = empRepo.save(employee);
		System.out.println("Saved Updated Employee:" + e);
		return EmployeeAdapter.convert(e);
	}

}
