package com.hawaholidays.processlayer.aggregators;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import com.hawaholidays.processlayer.model.Package;

@Component
@Slf4j
public class PackageAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<Package> newPackages;
		
		if (oldExchange != null) {
			List<Package> cabsAndHotels = oldExchange.getIn().getBody(List.class);
			List<Package> transports = newExchange.getIn().getBody(List.class);
			newPackages = productOfCabsAndHotelsAndTransports(cabsAndHotels, transports);
			newExchange.getIn().setBody(newPackages);
		}
		
		return newExchange;
	}

	private List<Package> productOfCabsAndHotelsAndTransports(List<Package> cabsAndHotels, List<Package> transports) {
		List<Package> newPackages = new ArrayList<>();
		
		for(Package cAndHPackage : cabsAndHotels) {
			for (Package transport : transports) {
				newPackages.add(Package.builder()
										.cab(cAndHPackage.getCab())
										.hotel(cAndHPackage.getHotel())
										.transport(transport.getTransport())
										.totalPrice(cAndHPackage.getCab().getPrice()
													+ cAndHPackage.getHotel().getPrice()
													+ transport.getTransport().getPrice())
										.build());
			}
		}
		
		return newPackages;
	}

}
