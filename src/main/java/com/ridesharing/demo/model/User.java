package com.ridesharing.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String gender;
    private String age;
    private Integer taken;
    private Integer offered;
}
