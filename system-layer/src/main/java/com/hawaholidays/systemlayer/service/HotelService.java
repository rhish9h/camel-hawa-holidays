package com.hawaholidays.systemlayer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawaholidays.systemlayer.model.Hotel;
import com.hawaholidays.systemlayer.repository.HotelRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> getHotels(String city) {
		List<Hotel> hotels = hotelRepository.findByCity(city);
		
		return hotels;
	}
	
}
