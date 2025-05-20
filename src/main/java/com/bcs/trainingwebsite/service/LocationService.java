package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findLocations(Integer locationId) {
        List<Location> locations = locationRepository.findLocationsBy(locationId);
        return locations;
    }
}
