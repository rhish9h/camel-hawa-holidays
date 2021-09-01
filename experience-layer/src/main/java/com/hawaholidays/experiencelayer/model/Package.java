package com.hawaholidays.experiencelayer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {
	private Hotel hotel;
	private Transport transport;
	private Cab cab;
	private Double totalPrice;
}
