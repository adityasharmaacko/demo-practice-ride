package com.ridesharing.demo.services;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.database.UsersManager;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@AllArgsConstructor
public class RideService {

    private final RidesManager ridesManager;

    private final UsersManager usersManager;

    private final GeneralUtils generalUtils;

    private final BonusUtils bonusUtils;

    /**
     * offering a ride
     * @param offeringRequestDto
     */
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

    /**
     * book a ride with user preference
     * @param rideRequestDto
     * @return
     */
    public List<Ride> createBooking(RideRequestDto rideRequestDto){
        if(!generalUtils.isUserExists(rideRequestDto.getUserName())){
            log.error("user not exists in database");
            throw new EntityNotFoundException("user not exists in database");
        }
        if(rideRequestDto.getSeats() > 2 || rideRequestDto.getSeats() < 1){
            log.error("Seats request should lie in range [1,2]");
        }
        ArrayList<Ride>validRide = new ArrayList<>();
        List<Ride>listOfValidRides = ridesManager.getRides(rideRequestDto.getSourceLocation(),
                rideRequestDto.getDestinationLocation(),
                rideRequestDto.getSeats());
        if(rideRequestDto.getMostVacant()){
            AtomicReference<Integer> maxAvailability = new AtomicReference<>(Integer.MIN_VALUE);
            for(Ride ride : listOfValidRides){
                if(ride.getAvailableSeats() > maxAvailability.get()){
                    maxAvailability.set(Math.max(maxAvailability.get(), ride.getAvailableSeats()));
                    validRide.add(0,ride);
                }
            }
        }
        else{
            for(Ride ride : listOfValidRides){
                if(ride.getVehicleType().equalsIgnoreCase(rideRequestDto.getPreferredVehicle())){
                    validRide.add(0,ride);
                }
            }
        }
        if(validRide.size() == 0){
            validRide = bonusUtils.getRoute(rideRequestDto.getSourceLocation(),rideRequestDto.getDestinationLocation(),rideRequestDto.getSeats());
        }

        if(validRide.size() == 0){
            log.error("No direct ride present for this request");
            throw new GenericMessageException("No direct ride present for this request");
        }
        // update offered and taken count
        ridesManager.updateAvailableSeats(validRide,rideRequestDto.getSeats());
        usersManager.updateTakenCount(rideRequestDto.getUserName());
        return validRide;
    }

    /**
     * get list of all available rides
     * @return
     */
    public List<Ride> getAllRides(){
        return ridesManager.getAllRidesList();
    }

    /**
     * end the status of a ride
     * @param userName
     * @param registrationNumber
     */
    public void endRideStatus(String userName, String registrationNumber){
        ridesManager.endRide(userName,registrationNumber);
    }

    /**
     * get offered/taken ride status of all users
     * @return
     */
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
