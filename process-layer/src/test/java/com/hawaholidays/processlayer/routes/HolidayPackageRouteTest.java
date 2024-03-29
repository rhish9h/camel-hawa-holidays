package com.hawaholidays.processlayer.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.hawaholidays.processlayer.model.Package;

@ActiveProfiles("test")
@SpringBootTest()
@MockEndpoints
class HolidayPackageRouteTest {

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getPackages")
	private MockEndpoint mockGetPackages;
	
	@EndpointInject("mock:getFlightsEnd")
	private MockEndpoint mockGetFlightsRest;
	
	@EndpointInject("mock:getRailwaysEnd")
	private MockEndpoint mockGetRailwaysRest;
	
	@EndpointInject("mock:getHotelsEnd")
	private MockEndpoint mockGetHotelsRest;
	
	@EndpointInject("mock:getCabsEnd")
	private MockEndpoint mockGetCabsRest;
	
	@Test
	void getPackagesTest() {
		
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
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getPackages", "{}", headers);
		List<Package> actualPackages = mockGetPackages.getExchanges().get(0).getIn().getBody(List.class);
		assertEquals(16, actualPackages.size());
	}

}
