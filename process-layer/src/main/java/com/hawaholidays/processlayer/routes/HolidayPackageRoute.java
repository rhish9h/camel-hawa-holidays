package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.aggregators.CabHotelAggregator;
import com.hawaholidays.processlayer.aggregators.PackageAggregator;
import com.hawaholidays.processlayer.aggregators.TransportAggregator;

@Component
public class HolidayPackageRoute extends RouteBuilder {
	
	@Autowired
	private CabHotelAggregator cabHotelAggregator;
	
	@Autowired
	private TransportAggregator transportAggregator;
	
	@Autowired
	private PackageAggregator packageAggregator;
	
	@Override
	public void configure() throws Exception {
		
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
			.to("{{route.getFlights.start}}", "{{route.getRailways.start}}")
			.end()
//			.log("${body}")
			.to("{{route.getFlightsAndRailways.end}}");
	}

}
