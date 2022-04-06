package com.ridesharing.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Vehicle {
    private String id;
    private String registrationNumber;
    private String vehicleOwner;
    private String vehicleType;
    private Boolean isAvailable;
}
