package com.hawaholidays.systemlayer.service;

import org.springframework.stereotype.Service;

import com.hawaholidays.systemlayer.model.Flight;

@Service
public class FlightService {

	public Flight getFlights(String source, String destination) {
		return new Flight(1, "Ryan Air", "BLR", "PUN", 2345);
	}
	
}
