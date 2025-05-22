package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.location.dto.DistrictInfo;
import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.persistance.district.District;
import com.bcs.trainingwebsite.persistance.district.DistrictMapper;
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
    private final DistrictMapper districtMapper;


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
        for (LocationInfo locationInfo : locationInfos) {
Optional<District> optionalDistrict = districtRepository.findById(locationInfo.getDistrictId());
Optional<DistrictInfo> optionalDistrictInfo = optionalDistrict.map(d -> districtMapper.toDistrictInfo(d));

        }
        return locationInfos;
    }



        }


