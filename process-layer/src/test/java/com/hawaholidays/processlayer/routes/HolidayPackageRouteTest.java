package com.hawaholidays.processlayer.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
class HolidayPackageRouteTest{

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getHotelsEnd")
	private MockEndpoint mockGetHotelsRest;
	
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

}
