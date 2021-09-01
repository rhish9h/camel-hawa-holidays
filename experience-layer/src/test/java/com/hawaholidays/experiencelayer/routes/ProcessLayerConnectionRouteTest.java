package com.hawaholidays.experiencelayer.routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest()
class ProcessLayerConnectionRouteTest{

	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject("mock:getPackagesProcessLayer")
	private MockEndpoint mockGetPackagesProcessLayer;
	
	@Test
	void testGetPackagesHeaders() throws InterruptedException {
		
		mockGetPackagesProcessLayer.returnReplyBody(new Expression() {
			
			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				return (T)"[]";
			}
		});
		
		mockGetPackagesProcessLayer.expectedHeaderReceived("source", "PUN");
		mockGetPackagesProcessLayer.expectedHeaderReceived("destination", "BLR");
		Map<String, Object> headers = new HashMap<>();
		headers.put("source", "PUN");
		headers.put("destination", "BLR");
		template.sendBodyAndHeaders("direct:getPackages", "{}", headers);
		mockGetPackagesProcessLayer.assertIsSatisfied();
	}

}
