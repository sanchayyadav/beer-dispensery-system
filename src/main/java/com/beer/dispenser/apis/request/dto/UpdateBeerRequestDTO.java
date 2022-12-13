package com.beer.dispenser.apis.request.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Class defining a request DTO to update beer information as input.
 *  
 * <p>
 * 	This class describes all the properties required to Beer Usage.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.Beer
 * @see		com.beer.dispenser.apis.entity.BeerUsage
 * @since	29th November 2022
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "List of properties which is required update beer usage to notice open/close time of beer tap")
public class UpdateBeerRequestDTO implements Serializable{

	private static final long serialVersionUID = -8236316321441520338L;
	
	@NotNull(message = "Status is required")
	@ApiModelProperty(value = "Status of beer is open/close",
	  		  		  example = "open/close",
	  		  		  required = true)
	private String status;
	
	@NotNull(message = "Updated At is required")
	@ApiModelProperty(value = "Noticing the time when beer tap is open/close",
	  		  		  example = "2022-09-07 23:41:16",
	  		  		  required = true)
	private LocalDateTime updated_at;
}
