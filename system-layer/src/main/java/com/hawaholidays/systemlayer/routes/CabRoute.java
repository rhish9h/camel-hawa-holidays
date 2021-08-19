package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.systemlayer.service.CabService;

@Component
public class CabRoute extends RouteBuilder{

	@Autowired
	CabService cabService;
	
	@Override
	public void configure() throws Exception {
		rest("/cabs")
			.consumes("application/json")
			.produces("application/json")
			.get("?destination={destination}")
			.route()
			.to("direct:getCabs")
			.endRest();
		
		from("direct:getCabs")
			.log("${header.source}")
			.bean(cabService, "getCabs(${header.destination})");
		
	}

}
