package com.beer.dispenser.apis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beer.dispenser.apis.service.impl.MyUserDetailsService;
import com.beer.dispenser.apis.utils.JwtRequestFilter;

/**
 * Sets up security configuration to allow calls only from authenticated users.
 * 
 * @author	Sanchay Yadav
 * @since	11th December 2022
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired 
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	private static final String[] AUTH_WHITELISTED_URLS = {
			   "/security/authenticate",
//			   "/dispenser/{id}/spending",
			   "/v2/api-docs",           // swagger
               "/webjars/**",            // swagger-ui webjars
               "/swagger-resources/**",  // swagger-ui resources
//               "/configuration/**",      // swagger configuration
   			   "/swagger-ui.html",

	};

	/**
	 * Used by the default implementation of authenticationManager() to attempt to obtain an AuthenticationManager. If overridden,
	 * the AuthenticationManagerBuilder should be used to specify the AuthenticationManager.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(myUserDetailsService);
	}

	/**
	 * Override this method to configure the HttpSecurity. 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().
		//antMatchers("/dispenser/**").denyAll().
//	    antMatchers("/dispenser/**").authenticated().
//	    antMatchers("/Security/authenticate").permitAll().

		anyRequest().authenticated().
		and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Ignore the url which need not to authenticated for users.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		   .antMatchers(AUTH_WHITELISTED_URLS);
	}

	/**
	 * Processes an Authentication request.
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Service interface for encoding passwords.
	 * @return	{ @code NoOpPasswordEncoder.getInstance() } 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}

