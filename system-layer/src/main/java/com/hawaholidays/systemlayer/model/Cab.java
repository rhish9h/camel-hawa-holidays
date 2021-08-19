package com.hawaholidays.systemlayer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cabId;
	private String name;
	private String type;
	private String destination;
	private double price;
	
}
