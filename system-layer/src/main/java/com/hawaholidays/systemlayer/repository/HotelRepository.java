package com.hawaholidays.systemlayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hawaholidays.systemlayer.model.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
	public List<Hotel> findByCity(String city);
}
