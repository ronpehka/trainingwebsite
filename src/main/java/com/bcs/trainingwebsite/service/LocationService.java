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
            getLocationImageIfExists(locationInfo);
        }
        for (LocationInfo locationInfo : locationInfos) {
            getDistrictNameIfExists(locationInfo);
        }
        return locationInfos;
    }


    private void getLocationImageIfExists(LocationInfo locationInfo) {
        locationImageRepository.findLocationImageBy(locationInfo.getLocationId())
                .map(LocationImage::getData)
                .map(Base64.getEncoder()::encodeToString)
                .ifPresent(locationInfo::setImageData);
    }

    private void getDistrictNameIfExists(LocationInfo locationInfo) {
        districtRepository.findById(locationInfo.getDistrictId())
                .map(districtMapper::toDistrictInfo)
                .map(DistrictInfo::getDistrictName)
                .ifPresent(locationInfo::setDistrictName);
    }

}


