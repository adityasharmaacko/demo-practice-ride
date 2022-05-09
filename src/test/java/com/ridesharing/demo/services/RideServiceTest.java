package com.ridesharing.demo.services;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.model.Ride;
import com.ridesharing.demo.model.User;
import com.ridesharing.demo.requests.OfferingRequestDto;
import com.ridesharing.demo.requests.RegisterUserRequestDto;
import com.ridesharing.demo.requests.RideRequestDto;
import com.ridesharing.demo.utils.BonusUtils;
import com.ridesharing.demo.utils.GeneralUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    private RideService rideService;
    @Mock private RidesManager ridesManager;
    @Mock private UsersManager usersManager;
    @Mock private GeneralUtils generalUtils;
    @Mock private BonusUtils bonusUtils;

    @BeforeEach
    void setup(){
        rideService = new RideService(ridesManager,usersManager,generalUtils,bonusUtils);
    }

    @Test
    void createRide() {
        //given
        OfferingRequestDto offeringRequestDto = OfferingRequestDto.builder()
                .userName("Aditya")
                .registrationNumber("HR07S7672")
                .availableSeats(5)
                .vehicleType("Ertiga")
                .sourceName("Dehradun")
                .destinationName("Delhi")
                .build();
        // when
        Mockito.when(generalUtils.isUserExists("Aditya")).thenReturn(true);
        Mockito.when(generalUtils.isVehicleExists("HR07S7672")).thenReturn(true);
        Mockito.when(generalUtils.isVehicleBelongsToUser(offeringRequestDto)).thenReturn(true);
        rideService.createRide(offeringRequestDto);
        // then
        ArgumentCaptor<Ride> rideArgumentCaptor = ArgumentCaptor.forClass(Ride.class);
        verify(ridesManager).createRide(rideArgumentCaptor.capture());
    }

    @Test
    void getAllRides() {
        // when
        rideService.getAllRides();
        // then
        verify(ridesManager).getAllRidesList();
    }

    @Test
    void getAllUserRidesStatus() {
        // when
        rideService.getAllUserRidesStatus();
        // then
        verify(usersManager).getAllUsers();
    }
}