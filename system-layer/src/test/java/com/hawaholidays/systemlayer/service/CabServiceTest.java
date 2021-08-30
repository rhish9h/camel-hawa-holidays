package com.hawaholidays.systemlayer.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hawaholidays.systemlayer.model.Cab;
import com.hawaholidays.systemlayer.repository.CabRepository;

@ExtendWith(MockitoExtension.class)
class CabServiceTest {

	@Mock
	private CabRepository cabRepository;
	
	@Test
	void testGetCabsWithDestBLR() {
		List<Cab> mockedCabsList = new ArrayList<>();
		mockedCabsList.add(new Cab(1L, "Ola", "Micro", "BLR", 123));
		mockedCabsList.add(new Cab(2L, "Uber", "Mini", "BLR", 132));
		
		List<Cab> expectedCabsList = new ArrayList<>();
		expectedCabsList.add(new Cab(1L, "Ola", "Micro", "BLR", 123));
		expectedCabsList.add(new Cab(2L, "Uber", "Mini", "BLR", 132));
		
		when(cabRepository.findByDestination(Mockito.anyString())).thenReturn(mockedCabsList);
		CabService cabService = new CabService(cabRepository);
		List<Cab> actualOutput = cabService.getCabs("BLR");
		assertIterableEquals(expectedCabsList, actualOutput);
	}

}
