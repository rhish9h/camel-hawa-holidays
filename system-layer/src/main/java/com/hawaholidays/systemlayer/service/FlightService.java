package com.hawaholidays.systemlayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawaholidays.systemlayer.model.Flight;
import com.hawaholidays.systemlayer.repository.FlightRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;
	
	public List<Flight> getFlights(String source, String destination) {
		List<Flight> flights = flightRepository.findBySourceAndDestination(source, destination);
		
		return flights;
	}
	
}
