package com.beer.dispenser.apis.exception.handler.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Model for API error responses sent by the application.
 * 
 * <p>
 * 	Represents structure/list of properties which will be sent as API response in case an exception is thrown.
 * 	This class is only supposed to be used and initialized by {@code GlobalExceptionHandler}.
 * </p>
 *  
 * @author 	Sanchay Yadav
 * @see		com.naehas.workflow.exception.handler.GlobalExceptionHandler
 * @since	30th November 2022
 *
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 5736109107470197064L;

	private String errorMessage;

}
