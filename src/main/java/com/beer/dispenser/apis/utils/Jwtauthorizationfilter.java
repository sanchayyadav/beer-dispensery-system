package com.beer.dispenser.apis.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.beer.dispenser.apis.constants.WebSecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Service Implementation layer for JWT authorization filter.
 * 
 * @author	Sanchay Yadav
 * @since	11th December 2022
 */
@Service
public class Jwtauthorizationfilter {
	
	/**
	 * Validate registered username and password from generated token.
	 * 
	 * @param	token
	 * 			JWT 
	 * 
	 * @return	Whatever claims registered for payload in JWT.
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject );
	}
	
	/**
	 * Validate when token are getting expire.
	 * 
	 * @param	token
	 * 			JWT
	 * 
	 * @return	Expire time of token.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration );

	}
	
	/**
	 * Apply claims which is registered in payload.
	 * 
	 * @param	<T>
	 * 			Generic data type (i.e any data type is acceptable 
	 * 			like Date, String {@see extractUsername, extractExpiration} from above two methods.
	 * 
	 * @param	token
	 * 			JWT
	 * 
	 * @param	claimsResolver
	 * 			Whatever claims registered in payload it will apply by claimsResolver.
	 * 
	 * @return	{@code claimsResolver}
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
 	}

	/**
	 * Extract all claim from token which is in payload.
	 * 
	 * @param	token
	 * 			JWT
	 * 
	 * @return	All claims.
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(WebSecurityConstants.SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	/**
	 * Validate token is expired or not.
	 * 
	 * @param	token
	 * 			JWT
	 * 
	 * @return	true/false.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	/**
	 * Generate token according to user details which is already registered.
	 * 
	 * @param	userDetails
	 * 			Object contains details of user like user name and password.
	 * 
	 * @return	Generated token in string.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * Create a token according to claims in payload like expiry of token etc. and user details which is already registered.
	 * 
	 * @param	claims
	 * 			Map of claims which is in payload.
	 * 
	 * @param	subject
	 * 			It is a user name.
	 * 
	 * @return	Created token in string.
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, WebSecurityConstants.SECRET_KEY).compact();
	}
	
	/**
	 * Validate entered token is valid or not like username and password and signature matching or not.
	 * 
	 * @param	token
	 * 			JWT
	 * 
	 * @param	userDetails
	 * 			Object contains details of user like user name and password.
	 * 
	 * @return	true/false.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
