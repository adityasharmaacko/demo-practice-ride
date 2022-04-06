package com.ridesharing.demo.model;

import com.ridesharing.demo.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@Builder
public class Ride {
    private String id;
    private String userName;
    private String vehicleType;
    private String registrationNumber;
    private TripStatus status;
    private Integer availableSeats;
    private String sourceLocation;
    private String destinationLocation;
}
