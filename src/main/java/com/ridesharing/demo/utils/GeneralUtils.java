package com.ridesharing.demo.utils;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.database.UsersManager;
import com.ridesharing.demo.database.VehiclesManager;
import com.ridesharing.demo.model.Ride;
import com.ridesharing.demo.model.User;
import com.ridesharing.demo.model.Vehicle;
import com.ridesharing.demo.requests.OfferingRequestDto;
import com.ridesharing.demo.requests.RideRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GeneralUtils {

    @Autowired
    private UsersManager usersManager;

    @Autowired
    private VehiclesManager vehiclesManager;

    public Boolean isUserExists(String userName){
        List<User> users = usersManager.getAllUsers();
        for(User user : users){
            if(user.getUserName().equalsIgnoreCase(userName)){
                return true;
            }
        }
        return false;
    }

    public Boolean isVehicleExists(String registrationNumber){
        List<Vehicle> vehicles = vehiclesManager.getAllVehicles();
        for(Vehicle vehicle : vehicles){
            if(vehicle.getRegistrationNumber().equalsIgnoreCase(registrationNumber)){
                return true;
            }
        }
        return false;
    }

    public Boolean isVehicleBelongsToUser(OfferingRequestDto offeringRequestDto){
        List<Vehicle> vehicles = vehiclesManager.getAllVehicles();
        for(Vehicle vehicle : vehicles){
            if(vehicle.getRegistrationNumber().equalsIgnoreCase(offeringRequestDto.getRegistrationNumber()) && vehicle.getVehicleOwner().equalsIgnoreCase(offeringRequestDto.getUserName())){
                return true;
            }
        }
        return false;
    }

}
