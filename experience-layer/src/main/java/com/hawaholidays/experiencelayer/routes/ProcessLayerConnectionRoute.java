package com.hawaholidays.experiencelayer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Component;

import com.hawaholidays.experiencelayer.model.Package;

@Component
public class ProcessLayerConnectionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		rest("/packages")
			.consumes("application/json")
			.produces("application/json")
			.get("?source={source}&destination={destination}")
			.route()
			.to("{{route.getPackages.start}}")
			.endRest();
		
		from("{{route.getPackages.start}}")
			.removeHeaders("CamelHttp*")
			.recipientList(simple("{{route.getPackagesProcessLayer.restEndpoint}}"))
			.unmarshal(new ListJacksonDataFormat(Package.class))
			.to("{{route.getPackages.end}}");
		
	}

}
