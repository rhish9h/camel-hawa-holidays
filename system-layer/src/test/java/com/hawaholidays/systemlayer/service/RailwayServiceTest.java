package com.hawaholidays.systemlayer.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hawaholidays.systemlayer.model.Railway;
import com.hawaholidays.systemlayer.repository.RailwayRepository;

@ExtendWith(MockitoExtension.class)
class RailwayServiceTest {

	@Mock
	RailwayRepository railwayRepository;
	
	@Test
	void testGetRailwaysWithSourcePUNAndDestBLR() {
		final String SOURCE = "PUN";
		final String DESTINATION = "BLR";
		
		List<Railway> mockedRailways = new ArrayList<>();
		mockedRailways.add(new Railway(1L, "Rajdhani", "PUN", "BLR", 5673));
		mockedRailways.add(new Railway(2L, "Duranto", "PUN", "BLR", 5011));
		when(railwayRepository.findBySourceAndDestination(SOURCE, DESTINATION)).thenReturn(mockedRailways);
		
		List<Railway> expectedRailways = new ArrayList<>();
		expectedRailways.add(new Railway(1L, "Rajdhani", "PUN", "BLR", 5673));
		expectedRailways.add(new Railway(2L, "Duranto", "PUN", "BLR", 5011));
		
		RailwayService Railwayservice = new RailwayService(railwayRepository);
		List<Railway> actualRailways = Railwayservice.getRailways(SOURCE, DESTINATION);
		
		assertIterableEquals(expectedRailways, actualRailways);
	}

}
