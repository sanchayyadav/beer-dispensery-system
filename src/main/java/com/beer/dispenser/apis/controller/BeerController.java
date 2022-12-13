package com.beer.dispenser.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beer.dispenser.apis.exception.handler.response.ErrorResponse;
import com.beer.dispenser.apis.request.dto.BeerRequestDTO;
import com.beer.dispenser.apis.request.dto.UpdateBeerRequestDTO;
import com.beer.dispenser.apis.response.dto.BeerAndBeerUsageResponseDTO;
import com.beer.dispenser.apis.response.dto.BeerResponseDTO;
import com.beer.dispenser.apis.service.BeerService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST Controller for operations relating to beer dispensery system
 * 
 * <p>
 * 	All the RESTful web-services end point related to beer dispensery are defined here.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.Beer
 * @see 	com.beer.dispenser.apis.service.BeerService
 * @since	29th November 2022
 */
@RestController
@RequestMapping("/dispenser")
public class BeerController {
      
	@Autowired
	private BeerService beerService;
	
	/**
	 * REST API endpoint to create/save a beer dispensery.
	 * 
	 * @param	beerRequestDTO
	 * 			Request object to create a new beer dispensery.
	 * 
	 * @return	Details of newly create beer dispensery.
	 */
	@PostMapping(value = "",
				consumes=MediaType.APPLICATION_JSON_VALUE,
				produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message="Dispenser created correctly", response=BeerResponseDTO.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
	})
	public ResponseEntity<BeerResponseDTO> createBeerDispensery(@ApiParam(value = "List of properties to create a dispnesery.")
																@Validated @RequestBody BeerRequestDTO beerRequestDTO){
		
		BeerResponseDTO beerResponseDTO = beerService.createBeerDispensery(beerRequestDTO);
		
		return new ResponseEntity<>(beerResponseDTO, HttpStatus.OK);
	}
	
	/**
	 * Update details of beer dispensery.
	 * 
	 * @param	beerUsageId
	 * 			Id of a beer dispensery which we are going to update.
	 * 
	 * @param	updateBeerRequestDTO
	 * 			Request object to update in beer dispensery when tap is open/close.
	 * 
	 * @return	Message in response.
	 */
	@PutMapping(value="/{id}/status",
				consumes=MediaType.APPLICATION_JSON_VALUE,
				produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 202, message = "Status of the tap changed correctly"),
		@ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
	})
	public ResponseEntity<String> updateBeerDispensery(@PathVariable(name = "id", required = true) 
													   @ApiParam(value = "Id of beer to update dispnesery.", required = true, example = "1")
													   Long beerUsageId,
													   @Validated @RequestBody 
													   @ApiParam(value = "List of properties to update a status of tap in dispnesery.")
													   UpdateBeerRequestDTO updateBeerRequestDTO){
		
	    beerService.updateBeerDispensery(beerUsageId, updateBeerRequestDTO);
		
		return new ResponseEntity<>("Status of the tap changed correctly",HttpStatus.ACCEPTED);
	}
	
	/**
	 * Fetch all the information related to beer dispensery like : when tap is open/close, total spent.
	 * 
	 * @param	beerDispenseryId
	 * 			Id of beer dispensery which we are going to fetch.
	 * 
	 * @return	Details of beer dispnesery corresponding to input Id {@code beerDispenseryId}.
	 */
	@GetMapping(value="/{id}/spending",
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Total amount spent by the dispenser"),
		@ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
	})
	public ResponseEntity<BeerAndBeerUsageResponseDTO> getBeerDispensery(@PathVariable(name="id", required = true)
																		@ApiParam(value = "Id of beer dispensery to get amount and other details like - when tap is open/close, total spent.", required = true, example = "1")												
																		Long beerDispenseryId){
		
		BeerAndBeerUsageResponseDTO beerAndBeerUsageResponseDTO = beerService.getBeerDispensery(beerDispenseryId);
		
		return new ResponseEntity<>(beerAndBeerUsageResponseDTO, HttpStatus.OK);
	}
	
}
