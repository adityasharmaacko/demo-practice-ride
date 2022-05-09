package com.ridesharing.demo.services;

import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.model.Vehicle;
import com.ridesharing.demo.requests.RegisterVehicleRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    private VehicleService vehicleService;
    @Mock private VehiclesManager vehiclesManager;
    @BeforeEach
    void setup(){
        vehicleService = new VehicleService(vehiclesManager);
    }

    @Test
    void registerVehicle() {
        RegisterVehicleRequestDto registerVehicleRequestDto = RegisterVehicleRequestDto.builder()
                .vehicleType("Ertiga")
                .vehicleOwner("Aditya")
                .registrationNumber("HR07S7672")
                .build();
        vehicleService.registerVehicle(registerVehicleRequestDto);
        ArgumentCaptor<Vehicle>vehicleArgumentCaptor = ArgumentCaptor.forClass(Vehicle.class);
        verify(vehiclesManager).createVehicle(vehicleArgumentCaptor.capture());
    }

    @Test
    void getAllVehicles() {
        // when
        vehicleService.getAllVehicles();
        // then
        verify(vehiclesManager).getAllVehicles();
    }
}