package com.ridesharing.demo.services;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.enums.TripStatus;
import com.ridesharing.demo.exceptions.EntityNotFoundException;
import com.ridesharing.demo.exceptions.GenericMessageException;
import com.ridesharing.demo.model.Ride;
import com.ridesharing.demo.model.User;
import com.ridesharing.demo.requests.OfferingRequestDto;
import com.ridesharing.demo.requests.RideRequestDto;
import com.ridesharing.demo.responses.UserRideStatusResponse;
import com.ridesharing.demo.utils.BonusUtils;
import com.ridesharing.demo.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class RideService {

    @Autowired
    private RidesManager ridesManager;

    @Autowired
    private UsersManager usersManager;

    @Autowired
    private VehiclesManager vehiclesManager;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private BonusUtils bonusUtils;

    public void createRide(OfferingRequestDto offeringRequestDto){
        if(!generalUtils.isUserExists(offeringRequestDto.getUserName())){
            log.error("user not exists in database");
            throw new EntityNotFoundException("user not exists in database");
        }
        if(!generalUtils.isVehicleExists(offeringRequestDto.getRegistrationNumber())){
            log.error("vehicle not exists in database");
            throw new EntityNotFoundException("vehicle not exists in database");
        }
        if(!generalUtils.isVehicleBelongsToUser(offeringRequestDto)){
            log.error("ride data is not valid");
            throw new EntityNotFoundException("ride data is not valid");
        }
        Ride ride = Ride.builder()
                .id(UUID.randomUUID().toString())
                .userName(offeringRequestDto.getUserName())
                .status(TripStatus.NOT_STARTED)
                .availableSeats(offeringRequestDto.getAvailableSeats())
                .sourceLocation(offeringRequestDto.getSourceName())
                .destinationLocation(offeringRequestDto.getDestinationName())
                .vehicleType(offeringRequestDto.getVehicleType())
                .registrationNumber(offeringRequestDto.getRegistrationNumber())
                .build();
        ridesManager.createRide(ride);
    }

    public List<Ride> createBooking(RideRequestDto rideRequestDto){
        if(!generalUtils.isUserExists(rideRequestDto.getUserName())){
            log.error("user not exists in database");
            throw new EntityNotFoundException("user not exists in database");
        }
        Ride validRide = null;
        List<Ride>listOfValidRides = ridesManager.getRides(rideRequestDto.getSourceLocation(),
                rideRequestDto.getDestinationLocation(),
                rideRequestDto.getSeats());
        if(rideRequestDto.getMostVacant()){
            AtomicReference<Integer> maxAvailability = new AtomicReference<>(Integer.MIN_VALUE);
            for(Ride ride : listOfValidRides){
                if(ride.getAvailableSeats() > maxAvailability.get()){
                    maxAvailability.set(Math.max(maxAvailability.get(), ride.getAvailableSeats()));
                    validRide = ride;
                }
            }
        }
        else{
            for(Ride ride : listOfValidRides){
                if(ride.getVehicleType().equalsIgnoreCase(rideRequestDto.getPreferredVehicle())){
                    validRide = ride;
                }
            }
        }
//        Bonus - Case
//        List<String>pathExists = bonusUtils.bonusCase(rideRequestDto);
//        if(pathExists.size()>0){
//            return(bonusUtils.aggregateRides(pathExists,rideRequestDto.getSeats()));
//        }

        if(validRide == null){
            log.error("No direct ride present for this request");
            throw new GenericMessageException("No direct ride present for this request");
        }
        ridesManager.updateAvailableSeats(validRide,rideRequestDto.getSeats());
        usersManager.updateTakenCount(rideRequestDto.getUserName());
        return Collections.singletonList(validRide);
    }

    public List<Ride> getAllRides(){
        return ridesManager.getAllRidesList();
    }

    public void endRideStatus(String userName, String registrationNumber){
        ridesManager.endRide(userName,registrationNumber);
    }

    public List<UserRideStatusResponse> getAllUserRidesStatus(){
        List<User>getAllUsers = usersManager.getAllUsers();
        List<UserRideStatusResponse> userRideStatusResponses = new ArrayList<>();
        for(User user : getAllUsers){
            UserRideStatusResponse userRideStatusResponse = UserRideStatusResponse.builder().userName(user.getUserName()).offered(user.getOffered()).taken(user.getTaken()).build();
            userRideStatusResponses.add(userRideStatusResponse);
        }
        return userRideStatusResponses;
    }

}
