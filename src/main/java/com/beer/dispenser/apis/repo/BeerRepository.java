package com.beer.dispenser.apis.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beer.dispenser.apis.entity.Beer;

/**
 * Repository layer for operations related to Beer.
 * 
 * <p>
 * 	Extends {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}.
 * 	All the database operations relating to beer are executed from this interface.
 * </p>
 * 
 * <p>
 * 	Inherits {@code JpaRepository} methods with built in functionalities such as {@code save, findAll, findById}.
 * 	It also supports searching by property, and also has built-in support for both paging and sorting. 
 * </p>
 * 
 * @author 	Sanchay Yadav
 * @see		org.springframework.data.jpa.repository.JpaRepository
 * @see		com.beer.dispenser.apis.entity.Beer
 * @see		com.beer.dispenser.apis.service.BeerService
 * @since	28th November 2022
 */
@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

	/**
	 * Fetches beer on the basis of its id.
	 * 
	 * @param id
	 * 		  Id of the Beer.
	 * 
	 * @return {@link Optional} of Beer.
	 */
	Optional<Beer> findById(Long id);
}
