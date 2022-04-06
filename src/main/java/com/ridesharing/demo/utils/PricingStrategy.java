package com.ridesharing.demo.utils;

import com.ridesharing.demo.constants.AppConstants;
import com.ridesharing.demo.model.Location;

public class PricingStrategy {

    public Double tripPrice(Location sourceLocation, Location destinationLocation){
        return (AppConstants.PriceConstants.PER_KM_RATE * sourceLocation.distance(destinationLocation));
    }

}
