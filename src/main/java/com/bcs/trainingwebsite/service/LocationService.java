package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.persistance.district.District;
import com.bcs.trainingwebsite.persistance.district.DistrictRepository;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationMapper;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.locationimage.LocationImage;
import com.bcs.trainingwebsite.persistance.locationimage.LocationImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationImageRepository locationImageRepository;
    private final DistrictRepository districtRepository;


    public List<LocationInfo> getAllLocations() {

        List<Location> locations = locationRepository.findAll();
        List<LocationInfo> locationInfos = locationMapper.toLocationInfos(locations);

        for (LocationInfo locationInfo : locationInfos) {
            Optional<LocationImage> optionalLocationImage = locationImageRepository.findLocationImageBy(locationInfo.getLocationId());
            optionalLocationImage.ifPresent(locationImage -> {
                // Chat GPT: Base64 encode the image data
                String base64Image = Base64.getEncoder().encodeToString(locationImage.getData());
                locationInfo.setImageData(base64Image);
            });

        }
        addDistrictInfoTo(locationInfos);

        return locationInfos;
    }

    private void addDistrictInfoTo(List<LocationInfo> locationInfos) {
        for (LocationInfo locationInfo : locationInfos) {
            Integer locationId = locationInfo.getLocationId();
            Optional<District> optionalDistrict = districtRepository.findByLocationId(locationId);
            optionalDistrict.ifPresent(district -> locationInfo.setDistrictName(district.getName()));

        }
    }

}
