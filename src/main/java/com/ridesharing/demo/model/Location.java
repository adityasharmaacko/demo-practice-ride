package com.ridesharing.demo.model;

import lombok.Builder;
import lombok.Data;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Builder
@Data
public class Location {
    private Double locationX;
    private Double locationY;

    public Double distance(Location newLocation){
        return sqrt(pow(this.locationX - newLocation.getLocationX(),2) + pow(this.locationY - newLocation.getLocationY(),2));
    }
}
