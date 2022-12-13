package com.beer.dispenser.apis.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * API response DTO/model representing properties of a beer and beer usage.
 * 
 * <p>
 * 	This class describes some of the properties of a beer and beer usage such as amount and list of there usages.
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
@ApiModel(description = "List of properties which is required to send response.")
public class BeerAndBeerUsageResponseDTO implements Serializable{

	private static final long serialVersionUID = 1732007309083237122L;
	
	@ApiModelProperty(value = "Amount/Bill of the beer", example = "19.1367")
	private BigDecimal amount;
	
	@ApiModelProperty(value = "List of beer usage with beer usage")
	private List<BeerUsageResponseDTO> usages;
}
