package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationMapper;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.locationimage.LocationImage;
import com.bcs.trainingwebsite.persistance.locationimage.LocationImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationImageRepository locationImageRepository;


    public List<LocationInfo> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);
        for (LocationInfo locationInfo : locationInfos) {
            Optional<LocationImage> optionalLocationImage = locationImageRepository.findLocationImageBy(locationInfo.getLocationId());
            if (optionalLocationImage.isPresent()) {
                LocationImage locationImage = optionalLocationImage.get();
                locationInfo.setImageData(Arrays.toString(locationImage.getData()));
            }
        }

        return locationInfos;
    }

}
