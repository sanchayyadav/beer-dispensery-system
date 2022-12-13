package com.beer.dispenser.apis.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Entity class for BeerUsage.
 * 
 * <p>
 * 	This class represent the table 'beer_usage' of the database.
 * 	All the fields with either @Column annotated refer to columns of the aforementioned table.
 * </p>
 * 
 * @author	Sanchay Yadav
 * @see		com.beer.dispenser.apis.entity.Beer
 * @since	26th November 2022
 */
@Entity
@Table(name="beer_usage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BeerUsage {

	/**
	 * Id of the beer usage.
	 * 
	 * <p>
	 * 	Cannot be updatable.
	 * </p>
	 */
	@Id
	@Column(name="id", updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * Store when a tap of beer dispensery is open.
	 * 
	 * <p>
	 * 	Can be updatable.
	 * </p>
	 */
	@Column(name="opened_at", updatable = true)
	private LocalDateTime openedAt;
	
	/**
	 * Store when a tap of beer dispensery is close.
	 * 
	 * <p>
	 * 	Can be updatable.
	 * </p>
	 */
	@Column(name="closed_at", updatable = true)
	private LocalDateTime closedAt;
	
	/**
	 * Calculate amount based on time difference between opening and closing time of beer tap.
	 * 
	 * <p>
	 * 	Can be updatable.
	 * </p>
	 */
	@Column(name="total_spent", updatable = true)
	private BigDecimal totalSpent;
	
	/**
	 * Id of the beer for which this beer usage is configured.
	 */
	@ManyToOne
	@JoinColumn(name="beer_id")
	@JsonBackReference
	private Beer beer;

	
}
