package com.hawaholidays.processlayer.routes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hawaholidays.processlayer.model.Cab;
import com.hawaholidays.processlayer.model.Hotel;
import com.hawaholidays.processlayer.model.Package;
import com.hawaholidays.processlayer.model.Transport;

@ActiveProfiles("test")
@SpringBootTest()
@MockEndpoints
class IntermediatePackageAggregationsRouteTest {

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getFlightsEnd")
	private MockEndpoint mockGetFlightsRest;
	
	@EndpointInject("mock:getRailwaysEnd")
	private MockEndpoint mockGetRailwaysRest;
	
	@EndpointInject("mock:getFlightsAndRailways")
	private MockEndpoint mockGetFlightsAndRailways;
	
	@EndpointInject("mock:getCabsAndHotels")
	private MockEndpoint mockGetCabsAndHotels;
	
	@EndpointInject("mock:getHotelsEnd")
	private MockEndpoint mockGetHotelsRest;
	
	@EndpointInject("mock:getCabsEnd")
	private MockEndpoint mockGetCabsRest;
	
	@Test
	void testGetFlightsAndRailways() throws InterruptedException {
		mockGetFlightsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"flightId\":1,\"name\":\"Air India\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":5234.0},{\"flightId\":4,\"name\":\"Indigo\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":5034.0}]";
			}
			
		});
		mockGetRailwaysRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"railwayId\":1,\"name\":\"Shatabdi\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":3500.0},{\"railwayId\":2,\"name\":\"Duranto\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":3900.0}]";
			}
			
		});
		
		List<Package> expectedPackages = new ArrayList<>();
		expectedPackages.add(Package.builder()
				.transport(new Transport(1, "flight", "Air India", "PUN", "BLR", 5234))
				.build());
		expectedPackages.add(Package.builder()
				.transport(new Transport(4, "flight", "Indigo", "PUN", "BLR", 5034))
				.build());
		expectedPackages.add(Package.builder()
				.transport(new Transport(1, "railway", "Shatabdi", "PUN", "BLR", 3500))
				.build());
		expectedPackages.add(Package.builder()
				.transport(new Transport(2, "railway", "Duranto", "PUN", "BLR", 3900))
				.build());
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getFlightsAndRailways", "{}", headers);
		List<Package> actualPackages = mockGetFlightsAndRailways.getExchanges().get(0).getIn().getBody(List.class);
		assertIterableEquals(expectedPackages, actualPackages);
	}
	
	@Test
	void testGetCabsAndHotels() {
		mockGetCabsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"cabId\":1,\"name\":\"Ola\",\"type\":\"Micro\",\"destination\":\"BLR\",\"price\":500.0},{\"cabId\":2,\"name\":\"Ola\",\"type\":\"Macro\",\"destination\":\"BLR\",\"price\":600.0}]";
			}
			
		});
		mockGetHotelsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"hotelId\":1,\"name\":\"Oyo\",\"guests\":2,\"city\":\"BLR\",\"price\":2100.0},{\"hotelId\":2,\"name\":\"Air BNB\",\"guests\":3,\"city\":\"BLR\",\"price\":3400.0}]";
			}
			
		});
		
		List<Package> expectedPackages = new ArrayList<>();
		expectedPackages.add(Package.builder()
									.hotel(new Hotel(1L, "Oyo", 2, "BLR", 2100))
									.cab(new Cab(1L, "Ola", "Micro", "BLR", 500))
									.build());
		
		expectedPackages.add(Package.builder()
									.hotel(new Hotel(1L, "Oyo", 2, "BLR", 2100))
									.cab(new Cab(2L, "Ola", "Macro", "BLR", 600))
									.build());
		
		expectedPackages.add(Package.builder()
									.hotel(new Hotel(2L, "Air BNB", 3, "BLR", 3400))
									.cab(new Cab(1L, "Ola", "Micro", "BLR", 500))
									.build());
		
		expectedPackages.add(Package.builder()
									.hotel(new Hotel(2L, "Air BNB", 3, "BLR", 3400))
									.cab(new Cab(2L, "Ola", "Macro", "BLR", 600))
									.build());
							
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getCabsAndHotels", "{}", headers);
		List<Package> actualPackages = mockGetCabsAndHotels.getExchanges().get(0).getIn().getBody(List.class);
		assertIterableEquals(expectedPackages, actualPackages);
	}

}
