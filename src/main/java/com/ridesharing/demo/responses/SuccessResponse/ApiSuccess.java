package com.ridesharing.demo.responses.SuccessResponse;

import com.ridesharing.demo.responses.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiSuccess {
    private final ApiResponse apiResponse;
    private HttpStatus status;

    public ApiSuccess(String successMessage) {
        apiResponse = new ApiResponse(successMessage);
        this.apiResponse.setSuccess(Boolean.TRUE);
        this.status = HttpStatus.ACCEPTED;
    }
}