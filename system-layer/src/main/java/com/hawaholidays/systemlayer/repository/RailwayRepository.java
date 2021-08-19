package com.hawaholidays.systemlayer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hawaholidays.systemlayer.model.Railway;

public interface RailwayRepository extends CrudRepository<Railway, Long> {
	public List<Railway> findBySourceAndDestination(String source, String destination);
}
