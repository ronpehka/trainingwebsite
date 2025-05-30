package com.bcs.trainingwebsite.persistance.location;

import com.bcs.trainingwebsite.controller.location.dto.LocationDto;
import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import org.mapstruct.*;

import java.util.List;
@Mapper()
public interface LocationMapper {
    @Mapping(source = "id", target = "locationId")
    @Mapping(source = "name", target = "locationName")
    @Mapping(source = "address", target = "locationAddress")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "openingHours", target = "openingHours")
    @Mapping(source = "imageUrl", target = "imageUrl")

    LocationInfo toLocationInfo(Location location);

    List<LocationInfo> toLocationInfos(List<Location> locations);



    @Mapping(source = "locationName", target = "name")
    @Mapping(source = "locationAddress", target = "address")
    @Mapping(source = "openingHours", target = "openingHours")
    @Mapping(source = "imageUrl", target = "imageUrl")
    Location toLocation (LocationDto locationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source="locationName", target="name")
    @Mapping(source="locationAddress", target="address")
    @Mapping(source="openingHours", target="openingHours")
    @Mapping(source="imageUrl", target="imageUrl")
    Location partialUpdate(@MappingTarget Location location, LocationDto locationDto);

}
