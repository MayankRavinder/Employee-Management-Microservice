package com.nc.spring.boot.mongo.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Address {

	
	
	private String houseNumber;
	private String city;
	private String country;

}
