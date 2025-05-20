package com.bcs.trainingwebsite.controller.location.dto;

import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    @GetMapping("/locations")
    public List<Location> findLocations(RequestParam Integer locationId) {
        List<Location> Locations = LocationService.findLocations(locationId);
        return  Locations;

    }
}
