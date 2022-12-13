package com.beer.dispenser.apis.models;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Class defining a request to accept authentication request as input.
 *  
 * <p>
 * 	This class describes all the properties required for authentication.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @since	5th December 2022
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "A property which is required create a json web token.")
public class AuthenticationRequest {

	private static final long serialVersionUID = -8236316321441520338L;
	
	@NotNull(message = "user name is required")
	@ApiModelProperty(value = "user name of user",
	  		  		  example = "India",
	  		  		  required = true)
	private String userName;
	
	@NotNull(message = "pass word name is required")
	@ApiModelProperty(value = "passowrd of user",
	  		  		  example = "India@123",
	  		  		  required = true)
	private String password;
}
