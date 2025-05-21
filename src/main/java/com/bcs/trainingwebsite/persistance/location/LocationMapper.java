package com.bcs.trainingwebsite.persistance.location;

import com.bcs.trainingwebsite.controller.traininginfo.dto.LocationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    @Mapping(source = "name", target = "locationName")
    @Mapping(source = "address", target = "address")
    LocationInfo toLocationInfo(Location location);

}