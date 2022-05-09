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
public class RegisterUserRequestDto {
    @NotNull(message = "user_name can not be null") private String userName;
    @NotNull(message = "gender can not be null") private String gender;
    @NotNull(message = "age can not be null") private String age;
}
