package com.beer.dispenser.apis.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * API response DTO/model representing properties of a beer usage.
 * 
 * <p>
 * 	This class describes some of the properties of a beer usage such as opened at, closed at and 2 more.
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
@ApiModel(description = "List of properties which is required to send response to another reponse.")
public class BeerUsageResponseDTO implements Serializable {

	private static final long serialVersionUID = 1732007309083237112L;
	
	@ApiModelProperty(value = "Time of when beer tap is opned", example = "2022-09-07 23:41:16")
	private LocalDateTime openedAt;
	
	@ApiModelProperty(value = "Time of when beer tap is closed", example = "2022-09-07 12:41:16")
	private LocalDateTime closedAt;
	
	@ApiModelProperty(value = "flow of beer", example = "0.8947")
	private Double flow_volume;
	
	@ApiModelProperty(value = "Total spent on a beer", example = "12.547")
	private BigDecimal totalSpent;
	

}
