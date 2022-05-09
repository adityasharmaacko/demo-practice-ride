package com.ridesharing.demo.utils;

import com.ridesharing.demo.constants.AppConstants;
import com.ridesharing.demo.model.Location;

/**
 * for now this class is not used as {use case dont need the pricing strategy}
 */
public class PricingStrategy {

    public Double tripPrice(Location sourceLocation, Location destinationLocation){
        return (AppConstants.PriceConstants.PER_KM_RATE * sourceLocation.distance(destinationLocation));
    }

}
