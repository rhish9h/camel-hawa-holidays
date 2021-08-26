package com.hawaholidays.processlayer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Railway {
	private long railwayId;
	private String name;
	private String source;
	private String destination;
	private double price;
}
