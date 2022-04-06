package com.ridesharing.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TripStatus {
    NOT_STARTED("yet to start"),
    IN_PROGRESS("in_progress"),
    FINISHED("finished");

    private final String value;

    @JsonValue
    public String toValue() {
        return value;
    }

}
