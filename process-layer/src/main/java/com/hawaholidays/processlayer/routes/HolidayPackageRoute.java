package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.model.Flight;

@Component
public class HolidayPackageRoute extends RouteBuilder {

	@Value("${restConfig.host}")
	private String host;
	
	@Value("${restConfig.port}")
	private int port;
	
	@Override
	public void configure() throws Exception {
		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json)
			.host(host)
			.port(port);
		
		rest("/packages")
			.consumes("application/json")
			.produces("application/json")
			.get("?source={source}&destination={destination}")
			.route()
			.to("direct:getFlights")
			.endRest();
		
		from("direct:getFlights")
			.setHeader("source", () -> "PUN")
			.setHeader("destination", () -> "BLR")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("rest:get:/camel/flights?bridgeEndpoint=true&source={source}&destination={destination}"))
			.unmarshal(new ListJacksonDataFormat(Flight.class))
			.log("${body}");
		
	}

}
