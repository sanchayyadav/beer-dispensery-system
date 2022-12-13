package com.beer.dispenser.apis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.beer.dispenser.apis.constants.ErrorMessageConstants;
import com.beer.dispenser.apis.exception.EntityNotFoundException;
import com.beer.dispenser.apis.models.AuthenticationRequest;
import com.beer.dispenser.apis.models.AuthenticationResponse;
import com.beer.dispenser.apis.service.AuthService;
import com.beer.dispenser.apis.utils.Jwtauthorizationfilter;

/**
 * Service Implementation layer for operations related to JWT generation to access beer dispensery.
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
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private MyUserDetailsService userDetailsService; 
	
	@Autowired
	private Jwtauthorizationfilter jwtauthorizationfilter;
	
	/**
	 * REST API endpoint to create a JWT for access beer dispensery system.
	 * 
	 * @param	authenticationRequest
	 * 			Request object to create a JWT for beer dispensery.
	 * 
	 * @return	Generated JWT in form of JSON {@code authenticationResponse}
	 * 
	 * @throws	Exception
	 * 			<li>
	 * 				If invalid credentials .
	 * 			</li> 
	 */
	@Override
	public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest)
			throws Exception {
		
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			List<String> errorParameters = new ArrayList<>();
			throw new EntityNotFoundException(ErrorMessageConstants.ERROR_INVALID_USERNAME_OR_PASSWORD, errorParameters);
		}
		final UserDetails userDetails  = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		
		if(!authenticationRequest.getUserName().equals(userDetails.getUsername())) {
			List<String> errorParameters = new ArrayList<>();

			throw new EntityNotFoundException(ErrorMessageConstants.ERROR_INVALID_USERNAME_OR_PASSWORD, errorParameters);
		}

		final String jwt = jwtauthorizationfilter.generateToken(userDetails);
		authenticationResponse.setJsonWebToken(jwt);
		
		return authenticationResponse;
	}

}
