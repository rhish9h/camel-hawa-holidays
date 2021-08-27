package com.hawaholidays.processlayer.aggregators;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.model.Flight;
import com.hawaholidays.processlayer.model.Package;
import com.hawaholidays.processlayer.model.Railway;
import com.hawaholidays.processlayer.model.Transport;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransportAggregator implements AggregationStrategy {

	private final String FLIGHT = "flight";
	private final String RAILWAY = "railway";
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		List<Package> packages = null;
		
		if (oldExchange == null) {
			packages = new ArrayList<>();
		} else {
			packages = oldExchange.getIn().getBody(List.class);
		}
		
		List<Object> facilities = newExchange.getIn().getBody(List.class);
		packages = addFacilitiesToPackages(facilities, packages);
		newExchange.getIn().setBody(packages);
		
		log.info(oldExchange == null ? "null" : oldExchange.getIn().getBody().toString());
		log.info(newExchange.getIn().getBody().toString());
		
		return newExchange;
	}

	private List<Package> addFacilitiesToPackages(List<Object> facilities, List<Package> packages) {
		if (facilities.size() == 0) {
			return null;
		}
		
		Object firstObject = facilities.get(0);
		
		if (firstObject.getClass() == Flight.class) {
			for (Object flight : facilities) {
				packages.add(Package.builder()
									.transport(mapFlightToTransport((Flight)flight))
									.build());
			}
		} else if (firstObject.getClass() == Railway.class) {
			for(Object railway : facilities) {
				packages.add(Package.builder()
						.transport(mapRailwayToTransport((Railway)railway))
						.build());
			}
		}
		
		return packages;
	}
	
	private Transport mapFlightToTransport(Flight flight) {
		return Transport.builder()
						.transportId(flight.getFlightId())
						.transportType(FLIGHT)
						.name(flight.getName())
						.source(flight.getSource())
						.destination(flight.getDestination())
						.price(flight.getPrice())
						.build();
	}
	
	private Transport mapRailwayToTransport(Railway railway) {
		return Transport.builder()
						.transportId(railway.getRailwayId())
						.transportType(RAILWAY)
						.name(railway.getName())
						.source(railway.getSource())
						.destination(railway.getDestination())
						.price(railway.getPrice())
						.build();
	}
	
}
