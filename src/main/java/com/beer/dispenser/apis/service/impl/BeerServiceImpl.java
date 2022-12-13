package com.beer.dispenser.apis.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beer.dispenser.apis.constants.ErrorMessageConstants;
import com.beer.dispenser.apis.entity.Beer;
import com.beer.dispenser.apis.entity.BeerUsage;
import com.beer.dispenser.apis.exception.ConflictException;
import com.beer.dispenser.apis.exception.EntityNotFoundException;
import com.beer.dispenser.apis.mapper.BeerMapper;
import com.beer.dispenser.apis.repo.BeerRepository;
import com.beer.dispenser.apis.repo.BeerUsageRepository;
import com.beer.dispenser.apis.request.dto.BeerRequestDTO;
import com.beer.dispenser.apis.request.dto.UpdateBeerRequestDTO;
import com.beer.dispenser.apis.response.dto.BeerAndBeerUsageResponseDTO;
import com.beer.dispenser.apis.response.dto.BeerResponseDTO;
import com.beer.dispenser.apis.service.BeerService;

/**
 * Service Implementation layer for operations related to beer dispensery.
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
@Service
@Transactional
public class BeerServiceImpl implements BeerService {

	@Autowired 
	private BeerMapper beerMapper;
	
	@Autowired 
	private BeerRepository beerRepository;
	
	@Autowired
	private BeerUsageRepository beerUsageRepository;
	
	/**
	 * Create/save a beer dispensery.
	 * 
	 * @param	beerRequestDTO
	 * 			Request object to create a new beer dispensery.
	 * 
	 * @return	Details of newly create beer dispensery.
	 */
	@Override
	public BeerResponseDTO createBeerDispensery(BeerRequestDTO beerRequestDTO) {
		
		Beer beer = beerMapper.mapToBeer(beerRequestDTO);
		beer = beerRepository.save(beer);
		
		BeerResponseDTO beerResponseDTO = beerMapper.mapToBeerResponseDTO(beer);
		return beerResponseDTO;
	}

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
	@Override
	public Beer findById(Long id){

		List<String> errorParameters = new ArrayList<>();
		errorParameters.add(String.valueOf(id));
		
		Beer beer = beerRepository.findById(id)
		                          .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.ERROR_DISPENSER_DOES_NOT_EXISTS_WITH_ID, errorParameters));
		
		return beer;
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
	@Override
	public void updateBeerDispensery(Long beerUsageId, UpdateBeerRequestDTO updateBeerRequestDTO) {
		
		BeerUsage beerUsage = new BeerUsage();
		Beer beer = this.findById(beerUsageId);
		List<String> errorParameters = new ArrayList<>();
		List<BeerUsage> beerUsages = beerUsageRepository.findByBeer(beer);

		int beerUsagesSize = beerUsages.size()-1;

		if(beerUsages.size()==0 && updateBeerRequestDTO.getStatus().equals("close")) {
			
			errorParameters.add(String.valueOf(beerUsageId));
			throw new ConflictException(ErrorMessageConstants.ERROR_DISPENSER_NOT_OPEN_YET, errorParameters);
			
		} 
		else if((beerUsages.size()==0 && updateBeerRequestDTO.getStatus().equals("open") )
				||
				(beerUsages.get(beerUsagesSize).getOpenedAt()!=null && beerUsages.get(beerUsagesSize).getClosedAt()!=null) 
			    && 
			    updateBeerRequestDTO.getStatus().equals("open")){
			
			beerUsage.setBeer(beer);			
            beerUsage.setOpenedAt(updateBeerRequestDTO.getUpdated_at());
		} 
		else if(beerUsages.get(beerUsagesSize).getOpenedAt() == null 
				&& updateBeerRequestDTO.getStatus().equals("close")) {
			
			errorParameters.add(String.valueOf(beerUsageId));
			throw new ConflictException(ErrorMessageConstants.ERROR_DISPENSER_NOT_OPEN_YET, errorParameters);
		} 
		else if(beerUsages.get(beerUsagesSize).getOpenedAt() != null && updateBeerRequestDTO.getStatus().equals("open")
				                         ||
				beerUsages.get(beerUsagesSize).getClosedAt() != null && updateBeerRequestDTO.getStatus().equals("close")) {
			
			errorParameters.add(String.valueOf(beerUsageId));
			errorParameters.add(updateBeerRequestDTO.getStatus());
			
			throw new ConflictException(ErrorMessageConstants.ERROR_DISPENSER_SAME_AS_PREVOUS_STATUS, errorParameters);
		} 
		else if(beerUsages.get(beerUsagesSize).getOpenedAt() != null && updateBeerRequestDTO.getStatus().equals("close")) {
			
			beerUsage = beerUsageRepository.findById(beerUsages.get(beerUsagesSize).getId())
                    						.orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.ERROR_DISPENSER_DOES_NOT_EXISTS, errorParameters));
			beerUsage.setClosedAt(updateBeerRequestDTO.getUpdated_at());
		} 

		beerUsageRepository.save(beerUsage);		
	}

	/**
	 * Fetch all the information related to beer dispensery like : when tap is open/close, total spent.
	 * 
	 * @param	beerDispenseryId
	 * 			Id of beer dispensery which we are going to fetch.
	 * 
	 * @return	Details of beer dispnesery corresponding to input Id {@code beerDispenseryId}
	 * 
	 * @throws	EntityNotFoundException
	 * 			<li>
	 * 				If close time before then open time.
	 * 			</li>
	 */
	@Override
	public BeerAndBeerUsageResponseDTO getBeerDispensery(Long beerDispenseryId) throws EntityNotFoundException {
		
		BigDecimal amount = BigDecimal.ZERO;
		Beer beer = this.findById(beerDispenseryId);
		List<BeerUsage> beerUsages = beerUsageRepository.findByBeer(beer);
		
		List<String> errorParameters = new ArrayList<>();

		for(int i=0;i<beerUsages.size();++i) {
			
			LocalDateTime localOpenDateTime = beerUsages.get(i).getOpenedAt();
			LocalDateTime from = LocalDateTime.of(localOpenDateTime.getYear(), localOpenDateTime.getMonth(), localOpenDateTime.getDayOfMonth(),
					localOpenDateTime.getHour(), localOpenDateTime.getMinute(), localOpenDateTime.getSecond());
			
			if(beerUsages.get(i).getClosedAt()==null) {
		        beerUsages.get(i).setTotalSpent(BigDecimal.valueOf(1.23));
				amount= amount.add(beerUsages.get(i).getTotalSpent());
		        continue;   
			}
			LocalDateTime localCloseDateTime = beerUsages.get(i).getClosedAt();
			LocalDateTime to = LocalDateTime.of(localCloseDateTime.getYear(), localCloseDateTime.getMonth(), localCloseDateTime.getDayOfMonth(),
					localCloseDateTime.getHour(), localCloseDateTime.getMinute(), localCloseDateTime.getSecond());

			
	        Duration duration = Duration.between(from, to);
	        if(duration.getSeconds()<0) {
	        	errorParameters.add(String.valueOf(localCloseDateTime));
	        	errorParameters.add(String.valueOf(beerDispenseryId));
	        	errorParameters.add(String.valueOf(localOpenDateTime));

				throw new EntityNotFoundException(ErrorMessageConstants.ERROR_DISPENSER_CLOSING_TIME_LESS_THAN_OPENING_TIME, errorParameters);
	        }
	        beerUsages.get(i).setTotalSpent(BigDecimal.valueOf(duration.getSeconds()*(12.25)*beer.getFlow_volume()));
			amount= amount.add(beerUsages.get(i).getTotalSpent());

		}

		beer.setAmount(amount);
		beerRepository.save(beer);
        beerUsageRepository.saveAll(beerUsages);

		BeerAndBeerUsageResponseDTO beerAndBeerUsageResponseDTO = beerMapper.mapToBeerAndBeerUsage( beer, beerUsages);
        
		return beerAndBeerUsageResponseDTO;
	}
	
}
