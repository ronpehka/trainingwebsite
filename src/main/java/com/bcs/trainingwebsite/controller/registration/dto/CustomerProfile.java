package com.bcs.trainingwebsite.controller.registration.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerProfile {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String gender;
    private String password;


}
