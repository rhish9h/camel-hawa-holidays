package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.model.Cab;
import com.hawaholidays.processlayer.model.Flight;
import com.hawaholidays.processlayer.model.Hotel;
import com.hawaholidays.processlayer.model.Railway;

@Component
public class SystemLayerConnectionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("{{route.getFlights.start}}")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("{{route.getFlights.restEndpoint}}"))
			.unmarshal(new ListJacksonDataFormat(Flight.class))
			.log("${body}");
		
		from("{{route.getRailways.start}}")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("{{route.getRailways.restEndpoint}}"))
			.unmarshal(new ListJacksonDataFormat(Railway.class))
			.log("${body}");
		
		from("{{route.getCabs.start}}")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("{{route.getCabs.restEndpoint}}"))
			.unmarshal(new ListJacksonDataFormat(Cab.class))
			.log("${body}");
		
		from("{{route.getHotels.start}}")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("{{route.getHotels.restEndpoint}}"))
			.unmarshal(new ListJacksonDataFormat(Hotel.class))
			.log("${body}");
		
	}

}
