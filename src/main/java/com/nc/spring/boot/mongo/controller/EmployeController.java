package com.nc.spring.boot.mongo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nc.spring.boot.mongo.adapter.EmployeeAdapter;
import com.nc.spring.boot.mongo.model.Address;
import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeModifiedResponse;
import com.nc.spring.boot.mongo.model.EmployeeRequest;
import com.nc.spring.boot.mongo.model.EmployeeResponse;
import com.nc.spring.boot.mongo.service.EmployeeServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/employee-management/api")
@Api(value = "Employee Controller", description = "This api(Employee Controller will provide basic operation of employee")
public class EmployeController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	EmployeeServiceImpl empService;

	@InitBinder
	public void dataBinding(final WebDataBinder binder, final HttpServletRequest request) {
		System.out.println("InitBinder Method Called for :" + request.getRequestURI());

	}

	@ApiOperation(value = "Welcome page", notes = "Desc: Welcome to welcome page of Spring RestFul Api", hidden = false)
	@RequestMapping(value = "/welcome", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> welcomePage(HttpServletRequest request) {
		Map<String, Object> resquestDetails = new HashMap<>();
		resquestDetails.put("Title", "<br>Welcome to Spring Boot Mongo Application Running on port:8080</br>");
		return resquestDetails;
	}

	@ApiOperation(value = "Get Total Initialized Beans", notes = "Desc: Get the description of total number of Inititalized bean by spring contatiner", hidden = false)
	@RequestMapping(value = "/getTotalNumberOfBean", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getListOfAllBeansInitializedBySpring() {
		return appContext.getBeanDefinitionNames();
	}

	@ApiOperation(value = "Add Employee", notes = "Desc: Send Employee JSON Object to add with POST method", hidden = false)
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addEmployee(@RequestBody EmployeeRequest employeeRequest) throws IOException {
		System.out.println(employeeRequest.getId() + "\t" + employeeRequest.getName());
		Employee employee = EmployeeAdapter.convert(employeeRequest);
		System.out.println("Converted Request to entity:" + employee);
		employee = empService.create(employee);
		System.out.println("Entity After Saving" + employee);
		EmployeeResponse employeeResponse = EmployeeAdapter.convert(employee);
		System.out.println("Converted Entity to Response" + employeeResponse);
		return "<br>Employee Successfully Added</br>";
	}

	@ApiOperation(value = "Get All Employees", notes = "Desc: Fetch all the employee details form MongoBb", httpMethod = "GET", hidden = false)
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployees() {
		return empService.findAll();
	}

	@ApiOperation(value = "Delete Employee", notes = "Desc: Send Employee JSON Object which you want to delete", hidden = false)
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.DELETE)
	public void deleteEmployee(@RequestBody Employee tempEmployee) {
		empService.delete(tempEmployee);
		System.out.println("Deleted:");
	}

	@ApiOperation(value = "Delete All Employees", notes = "Desc: Delete all the employees from MongoDb database", hidden = false)
	@RequestMapping(value = "/deleteAllEmployee", method = RequestMethod.DELETE)
	public void deleteAllEmployee() {
		empService.deleteAll();
		System.out.println("All Employee Deleted ");
	}

	@ApiOperation(value = "Update Specific Employee", notes = "Desc: Send Employee JSON object with valid ID to update employee details", hidden = false)
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
	public String updateEmployeeDetailsById(@RequestBody Employee tempEmployee) {
		return empService.update(tempEmployee);
	}

	@ApiOperation(value = "Find Employee By Id(Mongo Default)", notes = "Desc: Send Employee JSON object with valid ID and get the corresponding employee details. This Api have used Mongo Default Method", hidden = false)
	@RequestMapping(value = "/findEmployee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee findEmployee(@RequestBody Employee tempEmployee) {
		return empService.find(tempEmployee);
	}

	@ApiOperation(value = "Find By Name", notes = "Desc: Send Employee JSON object with valid employee name and get the details. This Api have used custom method in mongoDB repository ", hidden = false)
	@RequestMapping(value = "/findByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeByName(@RequestBody Employee tempEmployee) {
		return empService.findByName(tempEmployee.getName());

	}

	@ApiOperation(value = "Find By Id", notes = "Desc: Send Employee JSON object with valid employee name.This Api have used custom method in MongoDB repository ", hidden = false)
	@RequestMapping(value = "/findById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeById(@RequestBody Employee tempEmployee) {
		return empService.findById(tempEmployee.getId());
	}

	@ApiOperation(value = "Get Employee By Id", hidden = false, notes = "Requires valid ID as input and return Employee object", response = Employee.class)
	@RequestMapping(value = "/getEmployeeById/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getEmployeeById(
			@ApiParam(value = "Employee Id From User") @PathVariable("employeeId") String employeeId) {
		return empService.findById(employeeId);
	}

	@ApiOperation(value = "Find All ID's Greater Than", notes = "Desc: Send Employee JSON object with valid ID and api returns all the employee having ID greater than specified", hidden = false)
	@RequestMapping(value = "/findIdGreaterThan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeByIdGreaterThan(@PathVariable("id") String id) {
		return empService.FindByIdGreaterThan(id);
	}

	@ApiOperation(value = "Find All ID's Less Than", notes = "Send Employee JSON object with valid ID and api returns all the employess having ID less than specified", hidden = false)
	@RequestMapping(value = "/findIdLessThan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeByIdLessThan(@PathVariable("id") String id) {
		return empService.FindByIdLessThan(id);
	}

	@ApiOperation(value = "Find ALL ID's Between form/to", notes = "Desc: This api accepts two input as a path param and return all the employee with in that range.Range will be decided by from/to", hidden = false)
	@RequestMapping(value = "/findIdGreaterBetween/{from}/{to}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeByIdBetween(@PathVariable("from") String from, @PathVariable("to") String to) {
		return empService.findByIdBetween(from, to);
	}

	@ApiOperation(value = "Count Employee", notes = "Desc: This api return total number of employee are currently in DB", hidden = false)
	@RequestMapping(value = "/countEmployees", method = RequestMethod.GET)
	public int findEmployeeByIdBetween() {
		return empService.countNumberOfEmployees();
	}

	@ApiOperation(value = "Get All Employees Ascending Order(ID's)", notes = "Desc: This api return all the list of employees in Ascending order based upon employee id", hidden = false)
	@RequestMapping(value = "/getByOrderByIdAllEmployeesAsc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployeesSortedByIdAsc() {
		return empService.getAllEmployeeSortedByIdAsc();
	}

	@ApiOperation(value = "Get All Employees Descending Order(ID's)", notes = "Desc: This api return all the list od employee in Descending order based upon employee id", hidden = false)
	@RequestMapping(value = "/getByOrderByIdAllEmployeesDesc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployeesSortedByIdDesc() {
		return empService.getAllEmployeeSortedByIdDesc();
	}

	@ApiOperation(value = "Get All Employees Ascending Order(Name's)", notes = "Desc: This api return list of all the employees in Ascending order based upon employee name", hidden = false)
	@RequestMapping(value = "/getByOrderByNameAllEmployeesAsc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployeesSortedByNameAsc() {
		return empService.getAllEmployeeSortedByNameAsc();
	}

	@ApiOperation(value = "Get All Employee Descending Order(Name's)", notes = "Desc: This api return list of all the employee in Descending order based uopn employee name", hidden = false)
	@RequestMapping(value = "/getByOrderByNameAllEmployeesDesc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployeesSortedByNameDesc() {
		return empService.getAllEmployeeSortedByNameDesc();
	}

	@ApiOperation(value = "Get Top 3 Employee (ID's)", notes = "This api return top N employee bases upon employee id")
	@RequestMapping(value = "/getTop3EmployeeBasedUponId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getTopNEmployeesBasedUponId() {
		return empService.getTop3EmployeeById();
	}

	@ApiOperation(value = "Get Top 3 Employee (Name's)", notes = "This api return top N employee bases upon employee name")
	@RequestMapping(value = "/getTop3EmployeeBasedUponName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getTopNEmployeeBasedUponName() {
		return empService.getTop3EmployeeByName();
	}

	@ApiOperation(value = "Get Distinct Employee", notes = "This apu return all the disticnt employees based upon name")
	@RequestMapping(value = "/getDistinctEmployees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getDistinctEmployee() {
		return empService.getDistinctByName();
	}

	@ApiOperation(value = "Get All Employees Using Iterator", notes = "This api will return all the employees of specified custom made Iteratar<String>.")
	@RequestMapping(value = "/getAllEmployeeIterable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getEmployeeUsingIterable() {
		Iterable<String> itr = Arrays.asList(new String[] { "1", "2", "3", "10" });
		return empService.getAllEmployeeBasedUponGivenId(itr);
	}

	@RequestMapping(value = "/getEmployeeNameById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getNameByEmployeeId(@PathVariable("id") String id) {
		System.out.println("hi EMPLOYEE ID:" + id);
		return empService.check(id);
	}

	@ApiOperation(value = "null will not be addded in response")
	@RequestMapping(value = "/getEmployeeSerializeCheck", method = RequestMethod.GET)
	public EmployeeModifiedResponse getEmployee() {
		System.out.println("Getting non-null fields only uing JSON_INCLUDE(NONNULL)");
		Employee e = new Employee();
		e.setId(new Integer(786).toString());
		Address address = new Address();
		address.setCountry("INDIA");
		e.setAddress(address);
		// Converting response to modifying response
		EmployeeModifiedResponse employeeModifiedResponse = new EmployeeModifiedResponse();
		employeeModifiedResponse.setValue(e);
		return employeeModifiedResponse;

	}

	@ApiOperation(value = "This api is used to fetch empl")
	@RequestMapping(value = "/getEmployeeByIdUsingQueryParam", params = "id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee getEmployeeByIdUsingQueryParam(@RequestParam(value="id") String employeeId) {
		System.out.println("Id is :" + employeeId);
		return empService.check(employeeId);
	}
	
	//Check Object to Entity Conversion
	
}
