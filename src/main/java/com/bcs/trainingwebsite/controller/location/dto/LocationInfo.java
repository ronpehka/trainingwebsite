package com.bcs.trainingwebsite.controller.location.dto;

import lombok.Data;

@Data
public class LocationInfo {
    private Integer locationId;
    private String locationName;
    private String locationAddress;
    private String imageData;
    private String locationDistrict;

}
