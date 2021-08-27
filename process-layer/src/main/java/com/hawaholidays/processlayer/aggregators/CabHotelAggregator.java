package com.hawaholidays.processlayer.aggregators;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.hawaholidays.processlayer.model.Cab;
import com.hawaholidays.processlayer.model.Hotel;
import com.hawaholidays.processlayer.model.Package;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CabHotelAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<Package> packages;
		
		if (oldExchange == null) {
			packages = new ArrayList<>();
		} else {
			packages = oldExchange.getIn().getBody(List.class);
		}

		List<Object> facilities = newExchange.getIn().getBody(List.class);
		packages = addFacilitiesToPackages(facilities, packages);
		newExchange.getIn().setBody(packages);
		log.info(newExchange.getIn().getBody().toString());
		return newExchange;
	}

	private List<Package> addFacilitiesToPackages(List<Object> facilities, List<Package> packages) {
		if (facilities.size() == 0) {
			return null;
		}
		
		Object firstObject = facilities.get(0);
		
		if (firstObject.getClass() == Cab.class) {
			for (Object cab : facilities) {
				packages.add(Package.builder()
							.cab((Cab)cab)
							.build());
			}
		} else if (firstObject.getClass() == Hotel.class) {
			List<Package> newPackages = new ArrayList<>();
			
			for (Object hotel : facilities) {
				for (Package curPackage : packages) {
					newPackages.add(Package.builder()
								.cab(curPackage.getCab())
								.transport(curPackage.getTransport())
								.hotel((Hotel) hotel)
								.build());
				}
			}
			packages = newPackages;
		} 
		
		return packages;
	}

}
