package com.hawaholidays.systemlayer.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hawaholidays.systemlayer.model.Hotel;
import com.hawaholidays.systemlayer.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

	@Mock
	HotelRepository hotelRepository;
	
	@Test
	void testGetHotelsWithCityBLR() {
		List<Hotel> mockedHotels = new ArrayList<>();
		List<Hotel> expectedHotels = new ArrayList<>();
		HotelService hotelService = new HotelService(hotelRepository);
		final String CITY = "BLR";
		
		mockedHotels.add(new Hotel(1L, "AirBNB", 3, "BLR", 2123));
		mockedHotels.add(new Hotel(1L, "Oyo", 2, "BLR", 1121));
		
		expectedHotels.add(new Hotel(1L, "AirBNB", 3, "BLR", 2123));
		expectedHotels.add(new Hotel(1L, "Oyo", 2, "BLR", 1121));
		
		when(hotelRepository.findByCity("BLR")).thenReturn(mockedHotels);
		
		List<Hotel> actualHotels = hotelService.getHotels(CITY);
		assertIterableEquals(expectedHotels, actualHotels);
	}

}
