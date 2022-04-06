package com.ridesharing.demo.responses.ErrorResponse;

import com.ridesharing.demo.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final ApiResponse apiResponse;
    private HttpStatus status;

    public ApiError(String errorMessage) {
        apiResponse = new ApiResponse(errorMessage);
        this.apiResponse.setSuccess(Boolean.FALSE);
        this.status = HttpStatus.BAD_GATEWAY;
    }
}
