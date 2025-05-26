package com.bcs.trainingwebsite.persistance.location;

import com.bcs.trainingwebsite.controller.location.dto.LocationDto;
import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper()
public interface LocationMapper {
    @Mapping(source = "id", target = "locationId")
    @Mapping(source = "name", target = "locationName")
    @Mapping(source = "address", target = "locationAddress")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "openinghours", target = "openingHours")

    LocationInfo toLocationInfo(Location location);
    List<LocationInfo> toLocationInfos(List<Location> locations);

    @Mapping(source = "", target = "")
    @Mapping(source = "", target = "")
    @Mapping(source = "", target = "")
    @Mapping(source = "", target = "")
    Location toLocation(LocationDto locationDto)

}
