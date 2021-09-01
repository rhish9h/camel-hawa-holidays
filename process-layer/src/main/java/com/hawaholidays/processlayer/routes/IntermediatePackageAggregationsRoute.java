package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.aggregators.CabHotelAggregator;
import com.hawaholidays.processlayer.aggregators.TransportAggregator;

@Component
public class IntermediatePackageAggregationsRoute extends RouteBuilder {

	@Autowired
	private CabHotelAggregator cabHotelAggregator;
	
	@Autowired
	private TransportAggregator transportAggregator;
	
	@Override
	public void configure() throws Exception {
		from("{{route.getCabsAndHotels.start}}")
			.multicast(cabHotelAggregator)
			.to("{{route.getCabs.start}}", "{{route.getHotels.start}}")
			.end()
			.to("{{route.getCabsAndHotels.end}}");
		
		from("{{route.getFlightsAndRailways.start}}")
			.multicast(transportAggregator)
			.to("{{route.getFlights.start}}", "{{route.getRailways.start}}")
			.end()
			.to("{{route.getFlightsAndRailways.end}}");
		
	}

}
