package com.bcs.trainingwebsite.controller.location.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LocationDto {
    private Integer districtId;
    private String locationName;
    private String locationAddress;
    private String openingHours;
    private String imageUrl;

}
