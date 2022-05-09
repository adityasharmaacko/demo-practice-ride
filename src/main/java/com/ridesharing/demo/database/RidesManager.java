package com.ridesharing.demo.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridesharing.demo.enums.TripStatus;
import com.ridesharing.demo.exceptions.EntityAlreadyPresentException;
import com.ridesharing.demo.exceptions.GenericMessageException;
import com.ridesharing.demo.model.Ride;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Slf4j
public class RidesManager {

    @Autowired
    private VehiclesManager vehiclesManager;
    @Autowired
    private UsersManager usersManager;

    private final ObjectMapper objectMapper;

    HashMap<String, List<Ride>>offeredRides;
    HashMap<String, List<Ride>>bookedRides;

    @SneakyThrows
    public void createRide(Ride newRide){
        if(!offeredRides.containsKey(newRide.getUserName())){
            offeredRides.put(newRide.getUserName(), new ArrayList<>());
        }
        List<Ride> allOfferedRides = this.getAllRidesList();
        for (Ride ride : allOfferedRides) {
            if (ride.getRegistrationNumber().equals(newRide.getRegistrationNumber())) {
                log.error("Ride already exists for this vehicle");
                throw new EntityAlreadyPresentException("Ride already exists for this vehicle");
            }
        }
        log.info("Adding new Ride with Details: {}", objectMapper.writeValueAsString(newRide));
        usersManager.updateOfferedCount(newRide.getUserName())  ;
        offeredRides.get(newRide.getUserName()).add(newRide);;
    }

    public ArrayList<Ride> getAllRidesList(){
        ArrayList<Ride>allRides = new ArrayList<>();
        for (var entry : offeredRides.entrySet()) {
            allRides.addAll(entry.getValue());
        }
        return allRides;
    }

    public List<Ride> getAllRidesListWithAvailableSeats(Integer minSeats){
        List<Ride>allRides = new ArrayList<>();
        for (var entry : offeredRides.entrySet()) {
            allRides.addAll(entry.getValue());
        }
        List<Ride>validRideswithMinSeats = new ArrayList<>();
        for(Ride ride : allRides){
            if(ride.getAvailableSeats() >= minSeats){
                validRideswithMinSeats.add(ride);
            }
        }
        return validRideswithMinSeats;
    }


    public List<Ride> getRides(String source, String destination, Integer seats){
        List<Ride>validRides = new ArrayList<>();
        for(Ride ride: getAllRidesList()){
            if(ride.getSourceLocation().equals(source) && ride.getDestinationLocation().equals(destination) &&
                    ride.getAvailableSeats()>=seats && ride.getStatus().equals(TripStatus.NOT_STARTED)){
                validRides.add(ride);
            }
        }
        return validRides;
    }

    @SneakyThrows
    public void updateAvailableSeats(ArrayList<Ride> validRide, Integer bookedSeats){
        for(Ride valid : validRide){
            List<Ride>ridesOfferedByThisUser = offeredRides.get(valid.getUserName());
            for(Ride ride : ridesOfferedByThisUser){
                if(ride.getRegistrationNumber().equals(valid.getRegistrationNumber())){
                    log.info("updating available seats of ride: {}",objectMapper.writeValueAsString(validRide));
                    Integer currentAvailableSeats = ride.getAvailableSeats();
                    if(currentAvailableSeats<bookedSeats){
                        log.error("ride available but seats are limited");
                        throw new GenericMessageException("ride available but seats are limited");
                    }
                    ride.setAvailableSeats(currentAvailableSeats-bookedSeats);
                }
            }
        }
    }

    @SneakyThrows
    public void endRide(String userName, String registrationNumber){
        List<Ride>userRides = offeredRides.get(userName);
        for(Ride ride : userRides){
            if(ride.getRegistrationNumber().equalsIgnoreCase(registrationNumber)){
                log.info("ending status of ride: {}",objectMapper.writeValueAsString(ride));
                ride.setStatus(TripStatus.FINISHED);
            }
        }
    }

}
