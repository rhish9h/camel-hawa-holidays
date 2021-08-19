package com.hawaholidays.systemlayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hawaholidays.systemlayer.model.Cab;

public interface CabRepository extends CrudRepository<Cab, Long> {
	public List<Cab> findByDestination(String destination);
}
