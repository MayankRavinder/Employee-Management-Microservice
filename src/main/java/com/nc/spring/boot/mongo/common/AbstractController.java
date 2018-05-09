package com.nc.spring.boot.mongo.common;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class AbstractController {
	//Provide Status only
	 protected <T> ResponseEntity<T> getResponse(final HttpStatus status) {
	        return buildResponseEntity(null, null, status);
	    }

	    protected <T> ResponseEntity<T> getResponse(final T entity, final HttpStatus status) {
	        return buildResponseEntity(entity, null, status);
	    }

	    protected <T> ResponseEntity<T> getResponse(final T entity, final HttpHeaders httpHeaders, final HttpStatus status) {
	        return buildResponseEntity(entity, httpHeaders, status);
	    }

	    protected <T> ResponseEntity<T> getResponse(final ResponseEntity<T> responseEntity) {
	        HttpHeaders newHeaders = null;
	        if (responseEntity.getHeaders() != null) {
	            newHeaders = new HttpHeaders();
	            newHeaders.putAll(responseEntity.getHeaders());
	        }

	        return buildResponseEntity(responseEntity.getBody(), newHeaders, responseEntity.getStatusCode());
	    }

	    private <T> ResponseEntity<T> buildResponseEntity(final T entity, HttpHeaders httpHeaders, final HttpStatus status) {
	        if (httpHeaders == null) {
	            httpHeaders = new HttpHeaders();
	        }
	        httpHeaders.setAccessControlMaxAge(100);
	        httpHeaders.setAccessControlAllowHeaders(Collections.singletonList("AUTHORIZATION"));
	       
	        return new ResponseEntity<>(entity, httpHeaders, status);
	    }
}
