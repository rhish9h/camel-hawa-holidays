package com.hawaholidays.processlayer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cab {

	private Long cabId;
	private String name;
	private String type;
	private String destination;
	private double price;
	
}
