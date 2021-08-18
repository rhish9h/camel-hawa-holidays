package com.hawaholidays.systemlayer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Flight {
	private long flightId;
	private String name;
	private String source;
	private String destination;
	private double price;
}
