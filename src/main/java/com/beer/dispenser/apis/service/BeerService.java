package com.beer.dispenser.apis.service;

import com.beer.dispenser.apis.entity.Beer;
import com.beer.dispenser.apis.exception.ConflictException;
import com.beer.dispenser.apis.exception.EntityNotFoundException;
import com.beer.dispenser.apis.request.dto.BeerRequestDTO;
import com.beer.dispenser.apis.request.dto.UpdateBeerRequestDTO;
import com.beer.dispenser.apis.response.dto.BeerAndBeerUsageResponseDTO;
import com.beer.dispenser.apis.response.dto.BeerResponseDTO;

/**
 * Service layer for operations related to beer dispensery.
 * 
 * <li>
 * 	<p>
 * 		All the business logic for operations related to beer dispnesery is performed by implementation of this interface.
 * 	</p>
 * 
 * 	<p>
 * 		Implementation(s) of this interface receive request from {@code BeerController} 
 *  	and interact with database layer i.e., {@code BeerRepository and BeerUsageRepository} to perform operations on database.
 * 	</p>
 * </li>
 * 
 * @author	Sanchay Yadav
 * @see	  	com.beer.dispenser.apis.entity.Beer
 * @see    	com.beer.dispenser.apis.service.impl.BeerServiceImpl
 * @see		com.beer.dispenser.apis.controller.BeerController
 * @since	29th November 2022
 */
public interface BeerService {

	/**
	 * Create/save a beer dispensery.
	 * 
	 * @param	beerRequestDTO
	 * 			Request object to create a new beer dispensery.
	 * 
	 * @return	Details of newly create beer dispensery.
	 */
	BeerResponseDTO createBeerDispensery(BeerRequestDTO beerRequestDTO);

	/**
	* Update details of beer dispensery.
	 * 
	 * @param	beerUsageId
	 * 			Id of a beer dispensery which we are going to update.
	 * 
	 * @param	updateBeerRequestDTO
	 * 			Request object to update in beer dispensery when tap is open/close.
	 * 
	 * @return	void
	 * 
	 * @throws	ConflictException
	 * 			<li>
	 * 				If status for Id {@code beerUsageId} is same as previous status.
	 * 			</li>
	 * 
	 * @throws	EntityNotFoundException
	 * 			<li>
	 * 				If Id {@code beerUsageId} doesn't exists.
	 * 			</li>
	 */
	void updateBeerDispensery(Long beerUsageId, UpdateBeerRequestDTO updateBeerRequestDTO) throws ConflictException, EntityNotFoundException;

	/**
	 * Fetches a beer dispensery with input {@code id}.
	 * 
	 * @param 	id
	 * 			Id of the beer dispensery to be fetched.
	 * 
	 * @return	Instance of the {@code Beer}.
	 * 
	 * @throws 	EntityNotFoundException
	 * 			<li>
	 * 				If beer dispensery with input {@code id} doesn't exists.
	 * 			</li>
	 */
	Beer findById(Long id) throws EntityNotFoundException;

	/**
	 * Fetch all the information related to beer dispensery like : when tap is open/close, total spent.
	 * 
	 * @param	beerDispenseryId
	 * 			Id of beer dispensery which we are going to fetch.
	 * 
	 * @return	Details of beer dispnesery corresponding to input Id {@code beerDispenseryId}
	 * 
	 * @throws	IllegalArgumentException
	 * 			<li>
	 * 				If close time before then open time.
	 * 			</li>
	 */
	BeerAndBeerUsageResponseDTO getBeerDispensery(Long beerDispenseryId) throws IllegalArgumentException;
	
}
