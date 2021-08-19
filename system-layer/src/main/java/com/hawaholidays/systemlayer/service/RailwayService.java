package com.hawaholidays.systemlayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawaholidays.systemlayer.model.Railway;
import com.hawaholidays.systemlayer.repository.RailwayRepository;

@Service
public class RailwayService {

	@Autowired
	private RailwayRepository railwayRepository;
	
	public List<Railway> getRailways(String source, String destination) {
		List<Railway> railways = railwayRepository.findBySourceAndDestination(source, destination);
		
		return railways;
	}
	
}
