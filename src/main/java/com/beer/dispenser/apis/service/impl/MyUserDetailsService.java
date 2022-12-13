package com.beer.dispenser.apis.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Sets up user name and password manually for authentication.
 * 
 * @author	Sanchay Yadav
 * @see 	org.springframework.security.core.userdetails.UserDetailsService
 * @since	11th December 2022
 */
@Service
public class MyUserDetailsService implements UserDetailsService{

	/**
	 * Locates the user based on the username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return new User("Sanchay", "Sanchay@1234", new ArrayList<>());
	}

}
