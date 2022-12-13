package com.beer.dispenser.apis.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.beer.dispenser.apis.service.impl.MyUserDetailsService;

/**
 * This class adds JWT authentication filter to authorize requests coming to the server.
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.service.impl.MyUserDetailsService
 * @see 	org.springframework.web.filter.OncePerRequestFilter
 * @see 	com.beer.dispenser.apis.utils.Jwtauthorizationfilter
 * @since	11th December 2022
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired 
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired 
	private Jwtauthorizationfilter jwtauthorizationfilter;

//	@Override
//	protected boolean shouldNotFilterAsyncDispatch() {
//		return true;
//		
//	}

	/**
	 * Provides filter to pass only authenticated requests to the server.
	 * 
	 * @param	request
	 * 			HTTP request object.
	 * 
	 * @param	response
	 * 			HTTP response object.
	 * 
	 * @param   chain
	 * 			Filter chain
	 * 
	 * @throws	IOException, ServletException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		
		String userName = null;
		String jwt = null;
		
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			userName = jwtauthorizationfilter.extractUsername(jwt);
		}
		
		if(userName !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
			
			if(jwtauthorizationfilter.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(userDetails);
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
