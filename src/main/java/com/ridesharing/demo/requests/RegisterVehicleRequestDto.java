package com.ridesharing.demo.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RegisterVehicleRequestDto {
    @NotNull(message = "registration_number can not be null") private String registrationNumber;
    @NotNull(message = "vehicle_owner can not be null") private String vehicleOwner;
    @NotNull(message = "vehicle_type can not be null") private String vehicleType;
}
