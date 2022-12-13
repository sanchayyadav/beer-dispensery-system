package com.beer.dispenser.apis.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Custom exception which can be thrown for when entity is not found for input parameter(s).
 * 
 * <p>
 * 	Extends java.lang.RuntimeException and uses constructors to define error messages.
 * </p>
 * 
 * <p>
 * 	This exception can be thrown by executing: {@code throw new EntityNotFoundException("message");} and is not required to be handled or caught.
 * </p>
 * 
 * @author 	Sanchay Yadav
 * @see		java.lang.RuntimeException
 * @since	29th November 2022
 * 
*/
@Getter
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 8189334409219032404L;
	private List<String> errorParameters;

	public EntityNotFoundException() {
		super();
	}
	
	/**
	 * Throws EntityNotFoundException with an error message.
	 * 
	 * @param 	errorMessage
	 * 			Error message to be shown to user when this exception is thrown.
	 * 
	 * @param   errorParameters
	 * 			Error parameters contain data which is required in user-facing error messages to provide user more 
	 *          meaningful error messages like name, id etc.      
	 */
	public EntityNotFoundException(String errorMessage, List<String> errorParameters) {
		super(errorMessage);
		this.errorParameters = errorParameters;

	}
	
	
}
