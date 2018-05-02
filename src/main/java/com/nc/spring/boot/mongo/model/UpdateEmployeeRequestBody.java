package com.nc.spring.boot.mongo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import lombok.Getter;

@Getter
public class UpdateEmployeeRequestBody extends EmployeeRequestBody {

	private final Map<String, Consumer<Employee>> updates;

	public UpdateEmployeeRequestBody() {
		this.updates = new HashMap<>();
	}

	@Override
	public void setName(String name) {
		System.out.println("Update called for name" + name);
		super.setName(name);
		updates.put("name", employee -> employee.setName(name));
		System.out.println("update.getName() : " + updates.size());
	}

	@Override
	public void setMobile(String mobile) {
		super.setMobile(mobile);
		updates.put("mobile", employee -> employee.setMobile(mobile));
	}

	@Override
	public void setAddress(Address address) {
		super.setAddress(address);
		updates.put("address", employee -> employee.setAddress(address));
	}

}
