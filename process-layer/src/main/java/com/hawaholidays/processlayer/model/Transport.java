package com.hawaholidays.processlayer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transport {
	private long transportId;
	private String transportType;
	private String name;
	private String source;
	private String destination;
	private double price;
}
