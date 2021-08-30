package com.hawaholidays.systemlayer.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hawaholidays.systemlayer.model.Flight;
import com.hawaholidays.systemlayer.repository.FlightRepository;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

	@Mock
	FlightRepository flightRepository;
	
	@Test
	void testGetFlightsWithSourcePUNAndDestBLR() {
		final String SOURCE = "PUN";
		final String DESTINATION = "BLR";
		
		List<Flight> mockedFlights = new ArrayList<>();
		mockedFlights.add(new Flight(1L, "Jet Airways", "PUN", "BLR", 5673));
		mockedFlights.add(new Flight(2L, "Spice Jet", "PUN", "BLR", 5011));
		when(flightRepository.findBySourceAndDestination(SOURCE, DESTINATION)).thenReturn(mockedFlights);
		
		List<Flight> expectedFlights = new ArrayList<>();
		expectedFlights.add(new Flight(1L, "Jet Airways", "PUN", "BLR", 5673));
		expectedFlights.add(new Flight(2L, "Spice Jet", "PUN", "BLR", 5011));
		
		FlightService flightService = new FlightService(flightRepository);
		List<Flight> actualFlights = flightService.getFlights(SOURCE, DESTINATION);
		
		assertIterableEquals(expectedFlights, actualFlights);
	}

}
