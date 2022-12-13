package com.beer.dispenser.apis.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beer.dispenser.apis.entity.Beer;
import com.beer.dispenser.apis.entity.BeerUsage;

/**
 * Repository layer for operations related to Beer Usage.
 * 
 * <p>
 * 	Extends {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}.
 * 	All the database operations relating to beer usage are executed from this interface.
 * </p>
 * 
 * <p>
 * 	Inherits {@code JpaRepository} methods with built in functionalities such as {@code save, findAll, findById}.
 * 	It also supports searching by property, and also has built-in support for both paging and sorting. 
 * </p>
 * 
 * @author 	Sanchay Yadav
 * @see		org.springframework.data.jpa.repository.JpaRepository
 * @see		com.beer.dispenser.apis.entity.BeerUsage
 * @see		com.beer.dispenser.apis.service.BeerService
 * @since	28th November 2022
 */
@Repository
public interface BeerUsageRepository extends JpaRepository<BeerUsage, Long> {

	/**
	 * Fetching list of beer usage based on beer.
	 * 
	 * @param	beer
	 * 			Beer from which beer usage going to fetch.
	 * 
	 * @return	List of beer usage.
	 */
	List<BeerUsage> findByBeer(Beer beer);

	/**
	 * Saving a beer usage details.
	 * 
	 * @param	beerUsages
	 * 		    List of beer usage which need to save.	
	 * 
	 * @return	void
	 */
	void save(List<BeerUsage> beerUsages);


}
