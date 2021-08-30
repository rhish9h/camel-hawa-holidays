package com.hawaholidays.systemlayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawaholidays.systemlayer.model.Cab;
import com.hawaholidays.systemlayer.repository.CabRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CabService {

	@Autowired
	private CabRepository cabRepository;
	
	public List<Cab> getCabs(String destination) {
		List<Cab> cabs = cabRepository.findByDestination(destination);
		
		return cabs;
	}
	
}
