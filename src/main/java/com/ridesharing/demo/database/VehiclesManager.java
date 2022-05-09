package com.ridesharing.demo.database;

import com.ridesharing.demo.exceptions.EntityAlreadyPresentException;
import com.ridesharing.demo.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class VehiclesManager {

    List<Vehicle> vehicles = new ArrayList<>();
    HashMap<String, Integer> vehiclesMap  = new HashMap<>();

    public void createVehicle(Vehicle vehicle){
        if(vehiclesMap.containsKey(vehicle.getRegistrationNumber())){
            log.error("vehicle already exists");
            throw new EntityAlreadyPresentException("vehicle already exists");
        }
        vehicles.add(vehicle);
        vehiclesMap.put(vehicle.getRegistrationNumber(),1);
    }


    public List<Vehicle> getAllVehicles(){
        return vehicles;
    }


}
