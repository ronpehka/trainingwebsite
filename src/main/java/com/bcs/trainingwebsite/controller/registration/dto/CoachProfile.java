package com.bcs.trainingwebsite.controller.registration.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoachProfile extends CustomerProfile {
    private String phoneNumber;
    private String description;
}
