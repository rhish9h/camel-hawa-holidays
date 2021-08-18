package com.hawaholidays.systemlayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hawaholidays.systemlayer.model.Flight;

public interface FlightRepository extends CrudRepository<Flight, Long> {
	public List<Flight> findBySourceAndDestination(String source, String destination);
}
