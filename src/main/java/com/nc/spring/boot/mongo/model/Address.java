package com.nc.spring.boot.mongo.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Address {

	private String houseNumber;
	private String city;
	private String country;

}
