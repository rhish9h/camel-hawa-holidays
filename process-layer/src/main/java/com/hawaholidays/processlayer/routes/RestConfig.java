package com.hawaholidays.processlayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder {

	@Value("${restConfig.host}")
	private String host;
	
	@Value("${restConfig.port}")
	private int port;
	
	@Override
	public void configure() throws Exception {
		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json)
			.host(host)
			.port(port);
	}
	
}
