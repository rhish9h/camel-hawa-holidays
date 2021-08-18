package com.hawaholidays.systemlayer.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FlightList {
	private List<Flight> flights;
	
	public FlightList() {
		flights = new ArrayList<Flight>();
	}
	
	public void addFlight(Flight flight) {
		flights.add(flight);
	}
}
