package com.hawaholidays.systemlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FlightRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.auto)
	        .port(8085);
		
		rest("/flights")
			.consumes("application/json")
			.produces("application/json")
			.get()
			.route()
			.setBody(constant("{\"hello\":\"hi\"}"))
			.endRest();
	}
	
}
