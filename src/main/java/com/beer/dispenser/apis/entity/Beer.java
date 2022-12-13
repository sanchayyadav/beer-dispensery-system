package com.beer.dispenser.apis.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entity class for Beer.
 * 
 * <p>
 * 	This class represent the table 'beer' of the database.
 * 	All the fields with either @Column annotated refer to columns of the aforementioned table.
 * </p>
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.BeerUsage
 * @since	26th November 2022
 */
@Entity
@Table(name="beer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Beer {

	/**
	 * Id of the beer.
	 * 
	 * <p>
	 * 	Cannot be updatable.
	 * </p>
	 */
	@Id
	@Column(name="id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Calculate amount based on sum of total spent in beer usage for input id.
	 * 
	 * <p>
	 * 	Cannot be updatable.
	 * </p>
	 */
	@Column(name="amount", updatable = true)
	private BigDecimal amount;
	
	/**
	 * Represent flow coming from beer tap in liter/second.
	 * 
	 * <ul>
	 * 	<p>
	 * 		Cannot be null.
	 * 	</p>
	 *  <p>
	 *  	Cannot be updatable.
	 *  </p>
	 * </ul>
	 * 
	 */
	@Column(name="flow_volume", nullable=false, updatable = false)
	private Double flow_volume;
	
	/**
	 * Set of beer id of this beer usage.
	 */
	@OneToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			   mappedBy="beer",
			   orphanRemoval=true)
	@JsonManagedReference
	private Set<BeerUsage> beerUsage;
	
	
}
