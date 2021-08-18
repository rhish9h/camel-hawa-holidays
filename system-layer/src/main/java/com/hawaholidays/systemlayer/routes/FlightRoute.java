package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.systemlayer.model.FlightList;
import com.hawaholidays.systemlayer.service.FlightService;

@Component
public class FlightRoute extends RouteBuilder {
	
	@Autowired
	private FlightService flightService;
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.auto)
	        .port(8085);
		
		rest("/flights")
			.consumes("application/json")
			.produces("application/json")
			.get("?source={source}&destination={destination}")
			.route()
			.to("direct:getFlights")
			.marshal().json(JsonLibrary.Jackson)
			.endRest();
		
		from("direct:getFlights")
		.log("${header.source}")
		.bean(flightService, "getFlights(${header.source}, ${header.destination})");
	}
}
