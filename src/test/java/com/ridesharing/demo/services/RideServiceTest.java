package com.ridesharing.demo.services;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.utils.BonusUtils;
import com.ridesharing.demo.utils.GeneralUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    private RideService rideService;
    @Mock private RidesManager ridesManager;
    @Mock private UsersManager usersManager;
    @Mock private VehiclesManager vehiclesManager;
    @Mock private GeneralUtils generalUtils;
    @Mock private BonusUtils bonusUtils;

    @BeforeEach
    void setup(){
        rideService = new RideService(ridesManager,usersManager,vehiclesManager,generalUtils,bonusUtils);
    }

    @Test
    void createRide() {
    }

    @Test
    void createBooking() {
    }

    @Test
    void getAllRides() {
        // when
        rideService.getAllRides();
        // then
        verify(ridesManager).getAllRidesList();
    }

    @Test
    void endRideStatus() {
    }

    @Test
    void getAllUserRidesStatus() {
        // when
        rideService.getAllUserRidesStatus();
        // then
        verify(usersManager).getAllUsers();
    }
}