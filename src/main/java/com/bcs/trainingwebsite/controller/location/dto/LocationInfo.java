package com.bcs.trainingwebsite.controller.location.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class LocationInfo {
    private Integer locationId;
    private String locationName;
    private String locationAddress;
    private String imageData;
    @Setter
    private String districtName;

}
