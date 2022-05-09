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
public class RideRequestDto {
    @NotNull(message = "user_name can not be null") private String userName;
    @NotNull(message = "source_location can not be null") private String sourceLocation;
    @NotNull(message = "destination_location can not be null") private String destinationLocation;
    @NotNull(message = "seats can not be null") private Integer seats;
    @NotNull(message = "preferred_vehicle can not be null") private String preferredVehicle;
    @NotNull(message = "most_vacant can not be null") private Boolean mostVacant;
}
