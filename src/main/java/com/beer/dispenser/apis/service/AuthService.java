package com.beer.dispenser.apis.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.beer.dispenser.apis.models.AuthenticationRequest;
import com.beer.dispenser.apis.models.AuthenticationResponse;

/**
 * Service layer for operations related to JWT generation to access beer dispensery.
 * 
 * <li>
 * 	<p>
 * 		All the business logic for operations related to JWT generation is performed by implementation of this interface.
 * 	</p>
 * 
 * 	<p>
 * 		Implementation(s) of this interface receive request from {@code JwtSpringSecurityContoller} 
 * 	</p>
 * </li>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.service.AuthService
 * @see		com.beer.dispenser.apis.controller.JwtSpringSecurityContoller
 * @since	10th December 2022
 */
public interface AuthService {

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
	 * 				If invalid credentials .
	 * 			</li> 
	 */
	AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception;
		
}
