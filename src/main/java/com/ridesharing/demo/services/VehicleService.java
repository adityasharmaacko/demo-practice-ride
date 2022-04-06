package com.ridesharing.demo.services;

import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.model.Vehicle;
import com.ridesharing.demo.requests.RegisterVehicleRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private VehiclesManager vehiclesManager;

    public void registerVehicle(RegisterVehicleRequestDto registerVehicleRequestDto){
        Vehicle vehicle = Vehicle.builder()
                .id(UUID.randomUUID().toString())
                .vehicleOwner(registerVehicleRequestDto.getVehicleOwner())
                .isAvailable(Boolean.TRUE)
                .registrationNumber(registerVehicleRequestDto.getRegistrationNumber())
                .vehicleType(registerVehicleRequestDto.getVehicleType())
                .build();
        vehiclesManager.createVehicle(vehicle);
    }

    public List<Vehicle> getAllVehicles(){
        return vehiclesManager.getAllVehicles();
    }
}
