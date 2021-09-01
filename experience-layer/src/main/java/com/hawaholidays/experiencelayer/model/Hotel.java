package com.hawaholidays.experiencelayer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
	private Long hotelId;
	private String name;
	private Integer guests;
	private String city;
	private double price;
}
