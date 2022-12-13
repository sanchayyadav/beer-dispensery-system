package com.beer.dispenser.apis.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * API response model representing properties of a JWT.
 * 
 * <p>
 * 	This class describes a properties of a authentication.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @since	3th December 2022
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "Return a json web token.")
public class AuthenticationResponse {

	@ApiModelProperty(value = "json web token (JWT)",
					  example = "erhiuir.iyeruir.9uruiiejrk")
	private String jsonWebToken;
}
