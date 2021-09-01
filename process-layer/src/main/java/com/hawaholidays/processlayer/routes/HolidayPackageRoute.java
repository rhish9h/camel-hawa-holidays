package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.aggregators.CabHotelAggregator;
import com.hawaholidays.processlayer.aggregators.PackageAggregator;
import com.hawaholidays.processlayer.aggregators.TransportAggregator;
import com.hawaholidays.processlayer.model.Cab;
import com.hawaholidays.processlayer.model.Flight;
import com.hawaholidays.processlayer.model.Hotel;
import com.hawaholidays.processlayer.model.Railway;

@Component
public class HolidayPackageRoute extends RouteBuilder {

	@Value("${restConfig.host}")
	private String host;
	
	@Value("${restConfig.port}")
	private int port;
	
	@Autowired
	private CabHotelAggregator cabHotelAggregator;
	
	@Autowired
	private TransportAggregator transportAggregator;
	
	@Autowired
	private PackageAggregator packageAggregator;
	
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
			.multicast(packageAggregator)
			.to("{{route.getCabsAndHotels.start}}", "{{route.getFlightsAndRailways.start}}")
			.endRest();
		
		from("{{route.getCabsAndHotels.start}}")
			.multicast(cabHotelAggregator)
			.to("{{route.getCabs.start}}", "{{route.getHotels.start}}");
		
		from("{{route.getFlightsAndRailways.start}}")
			.multicast(transportAggregator)
			.to("{{route.getFlights.start}}", "{{route.getRailways.start}}");
		
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
