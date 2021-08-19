package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.systemlayer.service.FlightService;

@Component
public class FlightRoute extends RouteBuilder {
	
	@Autowired
	private FlightService flightService;
	
	@Override
	public void configure() throws Exception {
		
		rest("/flights")
			.consumes("application/json")
			.produces("application/json")
			.get("?source={source}&destination={destination}")
			.route()
			.to("direct:getFlights")
			.endRest();
		
		from("direct:getFlights")
			.bean(flightService, "getFlights(${header.source}, ${header.destination})");
	}
}
