package com.beer.dispenser.apis.service.impl.beer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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
import com.beer.dispenser.apis.service.impl.BeerServiceImpl;

/**
 * Class containing JUnit Jupiter/5 test cases for 
 * {@link com.beer.dispenser.apis.service.impl.BeerServiceImpl BeerServiceImpl}.
 * 
 * <p>
 * 	This class contains all the Junit test cases for operations related to user management done in {@code BeerServiceImpl}.
 * 	It also uses {@code Mockito} to mock and inject dependencies that are otherwise {@code autowired} in service layer.
 * </p>
 * 
 * @author 	Sanchay Yadav
 * @see		com.beer.dispenser.apis.service.impl.BeerServiceImpl
 * @since	08th December 2022
 *
 */
public class BeerServiceImplTest {

	@InjectMocks
	private BeerServiceImpl beerService; 
	
	@Mock
	private BeerService beerService1; 

	@Mock
	private BeerRepository beerRepository;
	
	@Mock
	private BeerUsageRepository beerUsageRepository;
	
	@Spy
	private BeerMapper beerMapper = BeerMapper.BEER;
	
	private Beer beer;
	
	private BeerUsage beerUsage;
	
	/**
	 * Used to initialize data before each test case execution.
	 * 
	 * <p>
	 * 	This method is executed before execution of every test case in this class.
	 * 	Used to provide common data initialization for test cases.
	 * </p>
	 * 
	 * <p>
	 * 	If there is some common data objects that are duplicating for multiple test cases, 
	 * 	then initializing of that data can be done inside this method.
	 * </p>
	 * 
	 * @throws 	Exception
	 * 			If an error occurs during execution
	 */
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		beer = new Beer();
		beer.setId(1L);
		beer.setAmount(BigDecimal.valueOf(1.24));
		beer.setFlow_volume(0.0834);
		beer.setBeerUsage(this.getBeerUsage());

	}

	/**
	 * Fetches beer usage of a beer.
	 * 
	 * @return	Set of beer usage {@code beerUsages}
	 */
	private Set<BeerUsage> getBeerUsage() {
		Set<BeerUsage> beerUsages = new HashSet<>();
		beerUsage = new BeerUsage();
		beerUsage.setId(2L);
		beerUsage.setClosedAt(LocalDateTime.now());
		beerUsage.setOpenedAt(LocalDateTime.now());
		beerUsage.setTotalSpent(BigDecimal.valueOf(57.9475));
		beerUsage.setBeer(beer);
		beerUsages.add(beerUsage);
		return beerUsages;
	}
	
	/**
	 * Test case for testing "create beer dispensery" functionality.
	 * 
	 * <p>
	 * 	This test case is for testing the business logic of create beer dispenser 
	 * 	
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#createBeerDispensery createBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testCreateBeerDispensery() {
		BeerRequestDTO beerRequestDTO = new BeerRequestDTO();
		beerRequestDTO = this.getBeerRequestDTO();
		when(beerRepository.save(any(Beer.class)))
		     				.thenReturn(beer);
		
		BeerResponseDTO beerResponseDTO = beerService.createBeerDispensery(beerRequestDTO);
		
		assertDoesNotThrow(() -> new EntityNotFoundException());
		assertNotNull(beerResponseDTO);
		assertEquals(beer.getId(),beerResponseDTO.getId());
		assertEquals(beer.getFlow_volume(), beerResponseDTO.getFlow_volume());
		assertEquals(beerRequestDTO.getFlow_volume(), beerResponseDTO.getFlow_volume());

		verify(beerRepository, times(1)).save(any(Beer.class));
	}
	
	/**
	 * Test case for testing "update a beer dispensery" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery() {
		
		 Long beerId = 2L;
		 
		 UpdateBeerRequestDTO updateBeerRequestDTO = new UpdateBeerRequestDTO();
		 updateBeerRequestDTO = this.updateBeerDispensers();
		
		 when(beerRepository.findById(anyLong()))
						  .thenReturn(Optional.of(beer));
		 List<BeerUsage> beerUsages = this.getBeerUsages();
		 when(beerUsageRepository.findByBeer(any(Beer.class)))
								.thenReturn(beerUsages);
		 when(beerUsageRepository.save(any(BeerUsage.class)))
								.thenReturn(beerUsages.get(0));
		 
		 beerService.updateBeerDispensery(beerId, updateBeerRequestDTO);
		 assertDoesNotThrow(() -> new EntityNotFoundException());
		 assertDoesNotThrow(() -> new ConflictException());
		 assertDoesNotThrow(() -> new Exception());

		 assertEquals(beer, beerUsages.get(0).getBeer());
		 assertNotNull(beerUsages.get(0).getOpenedAt());
		 assertNotNull(beerUsages.get(0).getClosedAt());
         assertNotNull(beerUsages.get(0).getId());
		 
		 verify(beerUsageRepository, times(1)).save(any(BeerUsage.class));		
	}

	/**
	 * Test case for testing "update a beer dispensery when beer dispensery not exists" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery when beer dispensery not exists.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery_When_Dispensery_Not_Exists() {
	
		 Long beerId = -1L;
		 when(beerRepository.findById(anyLong()))
							.thenReturn(Optional.empty());
		
		 UpdateBeerRequestDTO updateBeerRequestDTO = this.updateBeerDispensers();
		
		 assertThrows(EntityNotFoundException.class,
				 ()->beerService.updateBeerDispensery(beerId, updateBeerRequestDTO)
				 );
		 verify(beerUsageRepository, times(0)).save(any(BeerUsage.class));
	}
	
	/**
	 * Test case for testing "update a beer dispensery when beer dispensery not exists and status of tap is close" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery when beer dispensery not exists and status of tap is close.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery_When_Beer_Not_Exists_And_Status_Is_Close() {
		 
		 Long beerId = 2L;
		 when(beerRepository.findById(anyLong()))
							.thenReturn(Optional.of(beer));
		
		 UpdateBeerRequestDTO updateBeerRequestDTO = this.updateBeerDispensers();
		 updateBeerRequestDTO.setStatus("close");
		 List<BeerUsage> beerUsage = new ArrayList<>();
		 when(beerUsageRepository.findByBeer(any(Beer.class)))
		 						 .thenReturn(beerUsage);
		 assertThrows(ConflictException.class,
				 ()->beerService.updateBeerDispensery(anyLong(), updateBeerRequestDTO)
				 );
		 verify(beerUsageRepository, times(0)).save(any(BeerUsage.class));
	}
	
	/**
	 * Test case for testing "update a beer dispensery when beer dispensery have same status of beer tap like previous status of tap on same beer id" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery when beer dispensery have same status of beer tap like previous status of tap on same beer i.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery_When_Beer_Have_Same_Status_As_Previous_Status_On_Same_Beer_Id() {
		
		Long beerId = 2L;
		
		when(beerRepository.findById(anyLong()))
						   .thenReturn(Optional.of(beer));
		UpdateBeerRequestDTO updateBeerRequestDTO = this.updateBeerDispensers();
		 List<BeerUsage> beerUsages = this.getBeerUsages();

		 beerUsage.setClosedAt(null);
		when(beerUsageRepository.findByBeer(any(Beer.class)))
								.thenReturn(beerUsages);
		assertThrows(ConflictException.class,
				 ()->beerService.updateBeerDispensery(anyLong(), updateBeerRequestDTO)
				 );
		 verify(beerUsageRepository, times(0)).save(any(BeerUsage.class));

	}
	
	/**
	 * Test case for testing "update a beer dispensery when beer dispensery have same status of beer tap like previous status of tap on same beer id" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery when beer dispensery have same status of beer tap like previous status of tap on same beer id.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery_When_Beer_Have_Close_Status_But_Previous_Status_Is_Open_On_Same_Beer_Id() {
		
		 Long beerId = 2L;
		
		 when(beerRepository.findById(anyLong()))
						   .thenReturn(Optional.of(beer));
		 UpdateBeerRequestDTO updateBeerRequestDTO = this.updateBeerDispensers();
		 List<BeerUsage> beerUsages = this.getBeerUsages();

		 when(beerUsageRepository.findById(anyLong()))
		 						 .thenReturn(Optional.of(beerUsage));
		 beerUsage.setClosedAt(null);
		 updateBeerRequestDTO.setStatus("close");
		 when(beerUsageRepository.findByBeer(any(Beer.class)))
								.thenReturn(beerUsages);
		 
		 beerService.updateBeerDispensery(beerId, updateBeerRequestDTO);
		 assertDoesNotThrow(() -> new EntityNotFoundException());
		 assertDoesNotThrow(() -> new ConflictException());
		 assertDoesNotThrow(() -> new Exception());

		 assertEquals(beer.getId(), beerUsages.get(0).getBeer().getId());
		 assertNotNull(beerUsages.get(0).getOpenedAt());
		 assertNotNull(beerUsages.get(0).getClosedAt());
         assertNotNull(beerUsages.get(0).getId());
		 
		 verify(beerUsageRepository, times(1)).save(any(BeerUsage.class));		
	}
	
	/**
	 * Test case for testing "update a beer dispensery when beer dispensery have status of beer tap is close and beer usage is null for that beer id" functionality.
	 *
	 * <p>
	 * 	This test case is for testing the business logic of update a beer dispensery when beer dispensery have status of beer tap is close and beer usage is null for that beer id.
	 *
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#updateBeerDispensery updateBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testUpdateBeerDispensery_When_Beer_Have_Close_Status_But_Previous_Not_Same_Beer_Id() {
		
		
		 Long beerId = 2L;
		
		 when(beerRepository.findById(anyLong()))
						   .thenReturn(Optional.of(beer));
		 UpdateBeerRequestDTO updateBeerRequestDTO = this.updateBeerDispensers();
		 List<BeerUsage> beerUsages = this.getBeerUsages();

		 when(beerUsageRepository.findById(anyLong()))
		 						 .thenReturn(Optional.of(beerUsage));
		 beerUsage.setClosedAt(null);
		 updateBeerRequestDTO.setStatus("close");
		
		 assertThrows(ConflictException.class,
				 ()->beerService.updateBeerDispensery(anyLong(), updateBeerRequestDTO)
				 );
		 verify(beerUsageRepository, times(0)).save(any(BeerUsage.class));

	}
	
	/**
	 * Test case for testing "fetch beer dispensery details" functionality.
	 * 
	 * <p>
	 * 	This test case is for testing the business logic of fetching list of beer dispensery details correct input configurations.
	 * 	
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#getBeerDispensery getBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testGetBeerDispensery() {
		
		Long beerDispenseryId = 1L;
		when(beerRepository.findById(anyLong()))
						   .thenReturn(Optional.of(beer));
		 List<BeerUsage> beerUsages = this.getBeerUsages();

		when(beerUsageRepository.findByBeer(any(Beer.class)))
								.thenReturn(beerUsages);
		BeerAndBeerUsageResponseDTO beerAndBeerUsageResponseDTO = beerService.getBeerDispensery(beerDispenseryId);

		assertDoesNotThrow(() -> new Exception());

		assertNotNull(beerAndBeerUsageResponseDTO);
		assertEquals(beerAndBeerUsageResponseDTO.getUsages().size(), beerUsages.size());
		assertEquals(beerAndBeerUsageResponseDTO.getAmount(), beer.getAmount());
		assertEquals(beerAndBeerUsageResponseDTO.getUsages().get(0).getFlow_volume(), beer.getFlow_volume());
		assertEquals(beerAndBeerUsageResponseDTO.getUsages().get(0).getClosedAt(), beerUsage.getClosedAt());
		assertEquals(beerAndBeerUsageResponseDTO.getUsages().get(0).getOpenedAt(), beerUsage.getOpenedAt());
		assertEquals(beerAndBeerUsageResponseDTO.getUsages().get(0).getTotalSpent(), beerUsage.getTotalSpent());

		verify(beerRepository, times(1)).save(any(Beer.class));
		verify(beerUsageRepository, times(1)).saveAll(any(List.class));
	}
	
	/**
	 * Test case for testing "when open time is greater than close time" functionality.
	 * 
	 * <p>
	 * 	This test case is for testing the business logic when open time is greater than close time.
	 * 	
	 * 	For service layer logic please refer to:
	 * 	{@linkcom.beer.dispenser.apis.service.impl.BeerServiceImpl#getBeerDispensery getBeerDispensery}. 
	 * </p>
	 */
	@Test
	void testGetBeerDispensery_When_OpenedAt_Is_Before_Than_ClosedAt() {
		
		Long beerDispenseryId = 1L;
		
		when(beerRepository.findById(anyLong()))
						   .thenReturn(Optional.of(beer));
		 List<BeerUsage> beerUsages = this.getBeerUsages();

		 LocalDateTime fromDate = LocalDateTime.of(2021, 12, 31, 0, 0, 0);
		 LocalDateTime toDate = LocalDateTime.of(2021, 12, 20, 0, 0, 0);
		
		 beerUsage.setOpenedAt(fromDate);
		 beerUsage.setClosedAt(toDate);
		 when(beerUsageRepository.findByBeer(any(Beer.class)))
								.thenReturn(beerUsages);

		assertThrows(EntityNotFoundException.class,
				     () -> beerService.getBeerDispensery(beerDispenseryId)
					);
		
		verify(beerRepository, times(0)).save(any(Beer.class));
		verify(beerUsageRepository, times(0)).saveAll(any(List.class));
	}
	
	/**
	 * Create a data for update beer dispenser.
	 * 
	 * @return	{@code updateBeerRequestDTO}
	 */
	private UpdateBeerRequestDTO updateBeerDispensers() {
		 UpdateBeerRequestDTO updateBeerRequestDTO = new UpdateBeerRequestDTO();
		 updateBeerRequestDTO.setStatus("open");
		 updateBeerRequestDTO.setUpdated_at(LocalDateTime.now());
		 
		 return updateBeerRequestDTO;
	}

	/**
	 * Fetch a data of beer dispenser.
	 * 
	 * @return	{@code beerRequestDTO}
	 */
	private BeerRequestDTO getBeerRequestDTO() {
		BeerRequestDTO beerRequestDTO = new BeerRequestDTO();
		beerRequestDTO.setFlow_volume(0.0834);
		return beerRequestDTO;
	}

	/**
	 * Fetches beer usage of a beer.
	 * 
	 * @return	List of beer usage {@code beerUsages}
	 */
	private List<BeerUsage> getBeerUsages() {
		List<BeerUsage> beerUsages = new ArrayList<>();

		beerUsage = new BeerUsage();
		beerUsage.setId(2L);
		beerUsage.setOpenedAt(LocalDateTime.now());
		beerUsage.setClosedAt(LocalDateTime.now());
		beerUsage.setTotalSpent(BigDecimal.valueOf(57.9475));
		beerUsage.setBeer(beer);
		beerUsages.add(beerUsage);
		return beerUsages;
	}
	
}

