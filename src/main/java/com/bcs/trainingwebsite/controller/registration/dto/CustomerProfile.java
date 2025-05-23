package com.bcs.trainingwebsite.controller.registration.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerProfile {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String password;
}
