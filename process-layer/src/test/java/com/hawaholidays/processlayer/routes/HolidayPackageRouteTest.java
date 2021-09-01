package com.hawaholidays.processlayer.routes;

import java.util.HashMap;
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

@ActiveProfiles("test")
@SpringBootTest
@MockEndpoints
class HolidayPackageRouteTest{

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getHotelsEnd")
	private MockEndpoint mockGetHotelsRest;
	
	@EndpointInject("mock:getCabsEnd")
	private MockEndpoint mockGetCabsRest;
	
	@EndpointInject("mock:getFlightsEnd")
	private MockEndpoint mockGetFlightsRest;
	
	@EndpointInject("mock:getRailwaysEnd")
	private MockEndpoint mockGetRailwaysRest;
	
	@Test
	void testGetHotels() throws InterruptedException {
		mockGetHotelsRest.expectedHeaderReceived("city", "BLR");
		mockGetHotelsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"hotelId\":1,\"name\":\"Oyo\",\"guests\":2,\"city\":\"BLR\",\"price\":2100.0},{\"hotelId\":2,\"name\":\"Air BNB\",\"guests\":3,\"city\":\"BLR\",\"price\":3400.0}]";
			}
			
		});
		template.sendBodyAndHeader("direct:getHotels", "{}", "city", "BLR");
		mockGetHotelsRest.assertIsSatisfied();
	}

	@Test
	void testGetCabs() throws InterruptedException {
		mockGetCabsRest.expectedHeaderReceived("destination", "BLR");
		mockGetCabsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"cabId\":1,\"name\":\"Ola\",\"type\":\"Micro\",\"destination\":\"BLR\",\"price\":500.0},{\"cabId\":2,\"name\":\"Ola\",\"type\":\"Macro\",\"destination\":\"BLR\",\"price\":600.0}]";
			}
			
		});
		template.sendBodyAndHeader("direct:getCabs", "{}", "destination", "BLR");
		mockGetCabsRest.assertIsSatisfied();
	}
	
	@Test
	void testGetFlights() throws InterruptedException {
		mockGetFlightsRest.expectedHeaderReceived("source", "PUN");
		mockGetFlightsRest.expectedHeaderReceived("destination", "BLR");
		mockGetFlightsRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"flightId\":1,\"name\":\"Air India\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":5234.0},{\"flightId\":4,\"name\":\"Indigo\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":5034.0}]";
			}
			
		});
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getFlights", "{}", headers);
		mockGetFlightsRest.assertIsSatisfied();
	}
	
	@Test
	void testGetRailways() throws InterruptedException {
		mockGetRailwaysRest.expectedHeaderReceived("source", "PUN");
		mockGetRailwaysRest.expectedHeaderReceived("destination", "BLR");
		mockGetRailwaysRest.returnReplyBody(new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[{\"railwayId\":1,\"name\":\"Shatabdi\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":3500.0},{\"railwayId\":2,\"name\":\"Duranto\",\"source\":\"PUN\",\"destination\":\"BLR\",\"price\":3900.0}]";
			}
			
		});
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getRailways", "{}", headers);
		mockGetRailwaysRest.assertIsSatisfied();
	}
}
