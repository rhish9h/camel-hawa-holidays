package com.hawaholidays.processlayer.routes;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

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

import com.hawaholidays.processlayer.model.Package;
import com.hawaholidays.processlayer.model.Transport;

@ActiveProfiles("test")
@SpringBootTest()
@MockEndpoints
class HolidayPackageRouteTest{

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getFlightsEnd")
	private MockEndpoint mockGetFlightsRest;
	
	@EndpointInject("mock:getRailwaysEnd")
	private MockEndpoint mockGetRailwaysRest;
	
	@EndpointInject("mock:getFlightsAndRailways")
	private MockEndpoint mockGetFlightsAndRailways;
	
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
		expectedPackages.add(new Package(null,
				new Transport(4, "flight", "Indigo", "PUN", "BLR", 5034),
				null,
				null));
		expectedPackages.add(new Package(null,
				new Transport(1, "railway", "Shatabdi", "PUN", "BLR", 3500),
				null,
				null));
		expectedPackages.add(new Package(null,
				new Transport(2, "railway", "Duranto", "PUN", "BLR", 3900),
				null,
				null));
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getFlightsAndRailways", "{}", headers);
		List<Package> actualPackages = mockGetFlightsAndRailways.getExchanges().get(0).getIn().getBody(List.class);
		assertIterableEquals(expectedPackages, actualPackages);
	}
	
}
