package com.nc.spring.boot.mongo.SwaggerConfiguration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration("employeeManagementMicroservice")
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nc.spring.boot.mongo.controller"))
				.paths((regex("/employee-management/api.*"))).build().groupName("Employee Management")
				.useDefaultResponseMessages(false).apiInfo(metaData());
	}

	private ApiInfo metaData() {

		ApiInfo apiInfo = new ApiInfo("Spring Boot REST API", // Tittle of Api
				"Spring Boot REST API getAllEmployee Api Documentation", // Description of Api
				"1.0", // Version of Api
				"Terms of service", //
				new Contact("Mayank", "http://someUrl", "mayank.jobwork@gmail.com"), // Api Contact Person
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");

		return apiInfo;
	}
}
