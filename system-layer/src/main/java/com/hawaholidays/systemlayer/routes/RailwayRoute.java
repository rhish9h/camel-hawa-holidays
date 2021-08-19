package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.systemlayer.service.RailwayService;

@Component
public class RailwayRoute extends RouteBuilder {

	@Autowired
	private RailwayService railwayService;
	
	@Override
	public void configure() throws Exception {
		
		rest("/railways")
			.consumes("application/json")
			.produces("application/json")
			.get("?source={source}&destination={destination}")
			.route()
			.to("direct:getrailways")
			.endRest();
		
		from("direct:getrailways")
		.log("${header.source}")
		.bean(railwayService, "getRailways(${header.source}, ${header.destination})");
	}
	
}
