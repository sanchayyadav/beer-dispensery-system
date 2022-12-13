package com.beer.dispenser.apis.exception.handler;

import java.util.List;
import java.util.Locale;

import org.hibernate.hql.internal.ast.ErrorReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.beer.dispenser.apis.exception.ConflictException;
import com.beer.dispenser.apis.exception.EntityNotFoundException;
import com.beer.dispenser.apis.exception.FileNotFoundException;
import com.beer.dispenser.apis.exception.handler.response.ErrorResponse;

/**
 * 
 * Common handler for all exceptions thrown in the application.
 * 
 * <p>
 * 	This class handles all exceptions, populates custom API error response model and returns it as part of API response.
 * </p>
 * 
 * @author  Sanchay Yadav
 * @see		com.beer.dispenser.apis.exception.handler.response.ErrorResponse
 * @see		org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @since	30th November 2022
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	MessageSource messageSource;
	
	/**
	 * Handles {@code EntityNotFoundException}.
	 * 
	 * <p>
	 * 	This exception handler is triggered when an {@link com.beer.dispenser.apis.exception.EntityNotFoundException EntityNotFoundException} is thrown.
	 * </p>
	 * 
	 * @param 	e
	 * 			Instance of {@code EntityNotFoundException}
	 * 
	 * @return	List of parameters defined in {@code ErrorResponse} as API response along with a HTTP status code
	 */
	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e){
		String errorMessage = messageSource.getMessage(e.getMessage(), null, Locale.ENGLISH);
		List<String> errorParameters = e.getErrorParameters();
		
		if(!CollectionUtils.isEmpty(errorParameters)) {
			errorMessage = String.format(errorMessage, errorParameters.toArray());
		}
		
		ErrorResponse errorResponse  = ErrorResponse.builder()
													.errorMessage(errorMessage)
													.build();
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles {@code ConflictException}.
	 * 
	 * <p>
	 * 	This exception handler is triggered when an {@link com.beer.dispenser.apis.exception.ConflictException ConflictException} is thrown.
	 * </p>
	 * 
	 * @param 	e
	 * 			Instance of {@code ConflictException}
	 * 
	 * @return	List of parameters defined in {@code ErrorResponse} as API response along with a HTTP status code
	 */
	@ExceptionHandler({ConflictException.class})
	public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e){
		String errorMessage = messageSource.getMessage(e.getMessage(), null, Locale.ENGLISH);
		List<String> errorParameters = e.getErrorParameters();
		
		if(!CollectionUtils.isEmpty(errorParameters)) {
			errorMessage = String.format(errorMessage, errorParameters.toArray());
		}
		
		ErrorResponse errorResponse  = ErrorResponse.builder()
													.errorMessage(errorMessage)
													.build();
		
		return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
	}
	
	/**
	 * Handles {@code FileNotFoundException}.
	 * 
	 * <p>
	 * 	This exception handler is triggered when an {@link com.beer.dispenser.apis.exception.FileNotFoundException FileNotFoundException} is thrown.
	 * </p>
	 * 
	 * @param 	e
	 * 			Instance of {@code FileNotFoundException}
	 * 
	 * @return	List of parameters defined in {@code ErrorResponse} as API response along with a HTTP status code
	 */
	@ExceptionHandler({FileNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException e){
		String errorMessage = messageSource.getMessage(e.getMessage(), null,Locale.ENGLISH);
		List<String> errorParmeters = e.getErrorParameters();
		
		if(!CollectionUtils.isEmpty(errorParmeters)) {
			errorMessage = String.format(errorMessage, errorParmeters.toArray());
		}
		ErrorResponse errorResponse = ErrorResponse.builder()
													.errorMessage(errorMessage)
													.build();
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
}
