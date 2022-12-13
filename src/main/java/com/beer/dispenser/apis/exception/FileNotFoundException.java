package com.beer.dispenser.apis.exception;

import java.util.List;

import lombok.Getter;

/**
 * Custom exception which can be thrown for when file is not found for input parameter(s).
 * 
 * <p>
 * 	Extends java.lang.RuntimeException and uses constructors to define error messages.
 * </p>
 * 
 * <p>
 * 	This exception can be thrown by executing: {@code throw new FileNotFoundException("errorMessage","message");} and is not required to be handled or caught.
 * </p>
 * 
 * @author 	Sanchay Yadav
 * @see		java.lang.RuntimeException
 * @since	30th November 2022
 * 
 */
@Getter
public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8189334409219032404L;
	private List<String> errorParameters;
	
	public FileNotFoundException() {
		super();
	}
	
	public FileNotFoundException(String errorMessage, List<String> errorParameters) {
		super(errorMessage);
		this.errorParameters=errorParameters;
	}
}
