package com.beer.dispenser.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beer.dispenser.apis.models.AuthenticationRequest;
import com.beer.dispenser.apis.models.AuthenticationResponse;
import com.beer.dispenser.apis.service.AuthService;

import io.swagger.annotations.ApiParam;

/**
 * REST Controller for operations related JWT generation to access beer dispensery system.
 * 
 * <p>
 * 	All the RESTful web-services end point related to authenticate beer dispensery are defined here.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.service.AuthService
 * @since	09th December 2022
 */
@RestController
@RequestMapping("/security")
public class JwtSpringSecurityContoller {

	@Autowired
	private AuthService authService;
	
	/**
	 * REST API endpoint to create a JWT for access beer dispensery system.
	 * 
	 * @param	authenticationRequest
	 * 			Request object to create a JWT for beer dispensery.
	 * 
	 * @return	Generated JWT in form of JSON.
	 * 
	 * @throws	Exception
	 * 			<li>
	 * 				If input credentials are invalid (i.e userName, password).
	 * 			</li> 
	 */
	@PostMapping(value = "/authenticate",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@ApiParam(value = "List of properties to create a json web token (JWT).")
																			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		AuthenticationResponse authenticationResponse = authService.createAuthenticationToken(authenticationRequest);

		return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
	}
}
