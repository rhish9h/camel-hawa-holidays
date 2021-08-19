package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.systemlayer.service.HotelService;

@Component
public class HotelRoute extends RouteBuilder {

	@Autowired
	private HotelService hotelService;
	
	@Override
	public void configure() throws Exception {
		
		rest("/hotels")
			.consumes("application/json")
			.produces("application/json")
			.get("?city={city}")
			.route()
			.to("direct:gethotels")
			.endRest();
		
		from("direct:gethotels")
			.bean(hotelService, "getHotels(${header.city})");
		
	}

}
