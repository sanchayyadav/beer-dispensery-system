package com.beer.dispenser.apis.request.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Class defining a request DTO to accept beer information as input.
 *  
 * <p>
 * 	This class describes all the properties required to Beer.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.Beer
 * @since	28th November 2022
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "A property which is required calculate beer amount")
public class BeerRequestDTO implements Serializable{

	private static final long serialVersionUID = -8236316321441520338L;
	
	@NotNull(message = "flow_volume is required")
	@ApiModelProperty(value = "flow volume of beer",
	  		  		  example = "0.0847",
	  		  		  required = true)
	private Double flow_volume;
}
