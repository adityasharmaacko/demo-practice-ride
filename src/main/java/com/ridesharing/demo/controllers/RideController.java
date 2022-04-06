package com.ridesharing.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridesharing.demo.requests.OfferingRequestDto;
import com.ridesharing.demo.requests.RideRequestDto;
import com.ridesharing.demo.responses.ApiResponse;
import com.ridesharing.demo.services.RideService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RideController {

    @Autowired
    private RideService rideService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/ride/register", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Object> registerRide(@RequestBody OfferingRequestDto offeringRequestDto){
        log.info("Offering a Ride: {}",objectMapper.writeValueAsString(offeringRequestDto));
        rideService.createRide(offeringRequestDto);
        return ResponseEntity.ok(new ApiResponse("API successful"));
    }

    @RequestMapping(value = "/ride/booking", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Object> registerBooking(@RequestBody RideRequestDto rideRequestDto){
        log.info("Booking a Ride: {}",objectMapper.writeValueAsString(rideRequestDto));
        return ResponseEntity.ok(rideService.createBooking(rideRequestDto));
    }

    @RequestMapping(value = "/rides/all", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<Object> getAllRides(){
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @RequestMapping(value = "/ride/end-ride", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Object> endRideStatus(@RequestParam(value = "user_name") String userName,
                                        @RequestParam(value = "registration_number") String registrationNumber){
        log.info("End a Ride with user_name {} and registration_number: {}",userName,registrationNumber);
        rideService.endRideStatus(userName,registrationNumber);
        return ResponseEntity.ok(new ApiResponse("API successful"));
    }

    @RequestMapping(value = "/rides/stats", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<Object> getUserRidesStatus(){
        return ResponseEntity.ok(rideService.getAllUserRidesStatus());
    }
}
