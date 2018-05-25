package com.nc.spring.boot.mongo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nc.spring.boot.mongo.adapter.EmployeeAdapter;
import com.nc.spring.boot.mongo.common.AbstractController;
import com.nc.spring.boot.mongo.model.Address;
import com.nc.spring.boot.mongo.model.Employee;
import com.nc.spring.boot.mongo.model.EmployeeModifiedResponse;
import com.nc.spring.boot.mongo.model.EmployeeRequestBody;
import com.nc.spring.boot.mongo.model.EmployeeResponseBody;
import com.nc.spring.boot.mongo.model.UpdateEmployeeRequestBody;
import com.nc.spring.boot.mongo.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/employee-management/api")
@Api(value = "Employee Controller", description = "This api(Employee Controller will provide basic operation of employee")
public class EmployeController extends AbstractController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private EmployeeService empService;

	@InitBinder
	public void dataBinding(final WebDataBinder binder, final HttpServletRequest request) {
		System.out.println("InitBinder Method Called for :" + request.getRequestURI());

	}

	@ApiOperation(value = "Welcome page", notes = "Desc: Welcome to welcome page of Spring RestFul Api", hidden = false)
	@RequestMapping(value = "/welcome", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> welcomePage(HttpServletRequest request) {
		Map<String, Object> requestDetails = new HashMap<String, Object>();
		requestDetails.put("Title", "<br>Welcome to Spring Boot Mongo Application Running on port:8080</br>");
		requestDetails.put("Port", "<br>Application Running on port:8080</br>");
		requestDetails.put("Application Domain", "<br>Employee Management Micreservice</br>");
		return getResponse(requestDetails, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Total Initialized Beans", notes = "Desc: Get the description of total number of Inititalized bean by spring contatiner", hidden = false)
	@RequestMapping(value = "/getTotalNumberOfBean", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String[]> getListOfAllBeansInitializedBySpring() {
		return getResponse(appContext.getBeanDefinitionNames(), HttpStatus.OK);
	}

	@ApiOperation(value = "Add Employee", notes = "Desc: Send Employee JSON Object to add with POST method", hidden = false)
	@RequestMapping(value = "/addEmployeeUsingMapper", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeResponseBody> addEmployeeMapper(@RequestBody EmployeeRequestBody employeeRequest)
			throws IOException {
		Employee employee = EmployeeAdapter.convert(employeeRequest);
		employee = empService.add(employee);
		System.out.println("Entity After Saving" + employee);
		EmployeeResponseBody employeeResponse = EmployeeAdapter.convert(employee);
		return getResponse(employeeResponse, HttpStatus.CREATED);
	}

	/*
	 * @ApiOperation(value = "Add Employee", notes =
	 * "Desc: Send Employee JSON Object to add with POST method", hidden = false)
	 * 
	 * @RequestMapping(value = "/addEmployeeWithoutMapper", method =
	 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<Employee> addEmployee(@RequestBody Employee employeeRequest)
	 * throws IOException { System.out.println(employeeRequest.getId() + "\t" +
	 * employeeRequest.getName()); Employee employee =
	 * empService.add(employeeRequest); System.out.println("Entity After Saving" +
	 * employee); return getResponse(employee, HttpStatus.OK); }
	 */

	@ApiOperation(value = "Get All Employees", notes = "Desc: Fetch all the employee details form MongoBb", httpMethod = "GET", hidden = false)
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return getResponse(empService.findAll(),HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Employee", notes = "Desc: Send Employee JSON Object which you want to delete", hidden = false)
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEmployee(@RequestBody Employee employeeRequestBody) {
		empService.delete(employeeRequestBody);
		return getResponse(HttpStatus.OK);
	}

	@ApiOperation(value = "Delete All Employees", notes = "Desc: Delete all the employees from MongoDb database", hidden = false)
	@RequestMapping(value = "/deleteAllEmployee", method = RequestMethod.DELETE)
	public void deleteAllEmployee() {
		empService.deleteAll();
	}

	@ApiOperation(value = "Update Specific Employee", notes = "Desc: Send Employee JSON object with valid ID to update employee details", hidden = false)
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<EmployeeResponseBody> updateEmployeeDetailsById(
			@RequestBody UpdateEmployeeRequestBody updateEmployeeRequestBody) {
		Employee employee = empService.findById((updateEmployeeRequestBody.getId()));
		System.out.println("Employee found:" + employee);
		return getResponse(empService.updateEmployee(employee, updateEmployeeRequestBody),HttpStatus.OK);
	}

	@ApiOperation(value = "Find Employee By Id(Mongo Default)", notes = "Desc: Send Employee JSON object with valid ID and get the corresponding employee details. This Api have used Mongo Default Method", hidden = false)
	@RequestMapping(value = "/findEmployeeById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> findEmployee(@RequestBody Employee employeeRequestBody) {
		System.out.println("Finding Id:" + employeeRequestBody.getId());
		return getResponse(empService.findById(employeeRequestBody.getId()),HttpStatus.OK);
	}

	@ApiOperation(value = "Find By Name", notes = "Desc: Send Employee JSON object with valid employee name and get the details. This Api have used custom method in mongoDB repository ", hidden = false)
	@RequestMapping(value = "/findByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> findEmployeeByName(@RequestBody Employee tempEmployee) {
		return getResponse(empService.findByName(tempEmployee.getName()),HttpStatus.OK);

	}

	@ApiOperation(value = "Get Employee By Id (Path param)", hidden = false, notes = "Requires valid ID as input and return Employee object", response = Employee.class)
	@RequestMapping(value = "/findEmployeeByOnlyId/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getEmployeeById(
			@ApiParam(value = "Employee Id From User") @PathVariable("employeeId") UUID employeeId) {
		return getResponse(empService.findById(employeeId),HttpStatus.OK);
	}

	@ApiOperation(value = "Find All ID's Greater Than", notes = "Desc: Send Employee JSON object with valid ID and api returns all the employee having ID greater than specified", hidden = false)
	@RequestMapping(value = "/findIdGreaterThan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> findEmployeeByIdGreaterThan(@PathVariable("id") UUID id) {
		return getResponse(empService.FindByIdGreaterThan(id),HttpStatus.OK);
	}

	@ApiOperation(value = "Find All ID's Less Than", notes = "Send Employee JSON object with valid ID and api returns all the employess having ID less than specified", hidden = false)
	@RequestMapping(value = "/findIdLessThan/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> findEmployeeByIdLessThan(@PathVariable("id") UUID id) {
		return getResponse(empService.FindByIdLessThan(id),HttpStatus.OK);
	}

	@ApiOperation(value = "Find ALL ID's Between form/to", notes = "Desc: This api accepts two input as a path param and return all the employee with in that range.Range will be decided by from/to", hidden = false)
	@RequestMapping(value = "/findIdGreaterBetween/{from}/{to}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> findEmployeeByIdBetween(@PathVariable("from") UUID from, @PathVariable("to") UUID to) {
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

	/*
	 * @ApiOperation(value = "Get All Employees Using Iterator", notes =
	 * "This api will return all the employees of specified custom made Iteratar<String>."
	 * )
	 * 
	 * @RequestMapping(value = "/getAllEmployeeIterable", method =
	 * RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) public
	 * List<Employee> getEmployeeUsingIterable() { Iterable<String> itr =
	 * Arrays.asList(new String[] { "1", "2", "3", "10" }); return
	 * empService.getAllEmployeeBasedUponGivenId(itr); }
	 */

	@RequestMapping(value = "/getEmployeeNameById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getNameByEmployeeId(@PathVariable("id") UUID id) {
		System.out.println("hi EMPLOYEE ID:" + id);
		return empService.check(id);
	}

	@ApiOperation(value = "null will not be addded in response")
	@RequestMapping(value = "/getEmployeeSerializeCheck", method = RequestMethod.GET)
	public EmployeeModifiedResponse getEmployee() {
		System.out.println("Getting non-null fields only uing JSON_INCLUDE(NONNULL)");
		Address address = new Address();
		address.setCountry("INDIA");
		Employee e = Employee.builder().id(UUID.randomUUID()).address(address).build();
		// Converting response to modifying response
		EmployeeModifiedResponse employeeModifiedResponse = new EmployeeModifiedResponse();
		employeeModifiedResponse.setValue(e);
		return employeeModifiedResponse;

	}

	@ApiOperation(value = "This api is used to fetch empl")
	@RequestMapping(value = "/getEmployeeByIdUsingQueryParam", params = "id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public Employee getEmployeeByIdUsingQueryParam(@RequestParam(value = "id") UUID employeeId) {
		System.out.println("Id is :" + employeeId);
		return empService.check(employeeId);
	}

	@ApiOperation(value = "This api is used to  send HTTP HEADERS")
	@RequestMapping(value = "/sendHeader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public HttpHeaders sendHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("headerName_1", "headerValue_1");
		headers.add("headerName_2", "headerValue_2");
		headers.add("headerName_3", "headerValue_3");
		return headers;

	}

	@ApiOperation(value = "This api is used to fetch  HTTP HEADERS")
	@RequestMapping(value = "/receieveHeader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
	public String receieveHeader(@RequestHeader("headerName_1") String headderValue) {
		System.out.println("Header: " + headderValue);
		return "Headers Received";
	}

}
