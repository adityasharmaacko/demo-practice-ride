package com.ridesharing.demo.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Builder
@ToString
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OfferingRequestDto {
    @NotNull(message = "user_name can not be null") private String userName;
    @NotNull(message = "source_name can not be null") private String sourceName;
    @NotNull(message = "destination_name can not be null") private String destinationName;
    @NotNull(message = "available seats can not be null") private Integer availableSeats;
    @NotNull(message = "vehicle_type can not be null") private String vehicleType;
    @NotNull(message = "registration_number can not be null") private String registrationNumber;
}
