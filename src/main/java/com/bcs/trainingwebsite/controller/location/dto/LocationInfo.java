package com.bcs.trainingwebsite.controller.location.dto;

import lombok.Data;
import lombok.Setter;
@Setter
@Data
public class LocationInfo {
    private Integer locationId;
    private String locationName;
    private String locationAddress;
    private String imageUrl;
    private Integer districtId;
    private String districtName;
    private String openingHours;

}
