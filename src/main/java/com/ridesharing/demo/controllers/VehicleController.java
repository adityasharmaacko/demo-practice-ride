package com.ridesharing.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridesharing.demo.requests.RegisterVehicleRequestDto;
import com.ridesharing.demo.responses.ApiResponse;
import com.ridesharing.demo.services.VehicleService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/vehicle/register", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Object> registerVehicle(@RequestBody RegisterVehicleRequestDto registerVehicleRequestDto){
        log.info("Register vehicle with details: {}",objectMapper.writeValueAsString(registerVehicleRequestDto));
        vehicleService.registerVehicle(registerVehicleRequestDto);
        return ResponseEntity.ok(new ApiResponse("API successful"));
    }

    @RequestMapping(value = "vehicles/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllVehicles(){
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

}
