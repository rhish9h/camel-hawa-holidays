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
			.to("direct:getCabsAndHotels", "direct:getFlightsAndRailways")
			.endRest();
		
		from("direct:getCabsAndHotels")
			.multicast(cabHotelAggregator)
			.to("direct:getCabs", "direct:getHotels");
		
		from("direct:getFlightsAndRailways")
			.multicast(transportAggregator)
			.to("direct:getFlights", "direct:getRailways");
		
		from("direct:getFlights")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("rest:get:/camel/flights?bridgeEndpoint=true&source={source}&destination={destination}"))
			.unmarshal(new ListJacksonDataFormat(Flight.class))
			.log("${body}");
		
		from("direct:getRailways")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("rest:get:/camel/railways?bridgeEndpoint=true&source={source}&destination={destination}"))
			.unmarshal(new ListJacksonDataFormat(Railway.class))
			.log("${body}");
		
		from("direct:getCabs")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("rest:get:/camel/cabs?bridgeEndpoint=true&destination={destination}"))
			.unmarshal(new ListJacksonDataFormat(Cab.class))
			.log("${body}");
		
		from("direct:getHotels")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("rest:get:/camel/hotels?bridgeEndpoint=true&city={destination}"))
			.unmarshal(new ListJacksonDataFormat(Hotel.class))
			.log("${body}");
	}

}
