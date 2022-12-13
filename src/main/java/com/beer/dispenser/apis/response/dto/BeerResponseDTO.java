package com.beer.dispenser.apis.response.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * API response DTO/model representing properties of a beer.
 * 
 * <p>
 * 	This class describes some of the properties of a beer such as id and flow volume.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.Beer
 * @see		com.beer.dispenser.apis.entity.BeerUsage
 * @since	30th November 2022
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "List of properties which is required at the of mapping DTO(s).")
public class BeerResponseDTO implements Serializable {

	private static final long serialVersionUID = -3236316321441520338L;
	
	@ApiModelProperty(value = "Id of the beer", example = "4")
	private Long id;
	
	@ApiModelProperty(value = "flow of beer", example = "0.8947")
	private Double flow_volume;
	
}
