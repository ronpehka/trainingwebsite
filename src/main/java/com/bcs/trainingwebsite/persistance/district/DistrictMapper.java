package com.bcs.trainingwebsite.persistance.district;

import com.bcs.trainingwebsite.controller.location.dto.DistrictInfo;
import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DistrictMapper {

    @Mapping(source = "id", target = "districtId")
    @Mapping(source = "name", target = "districtName")

    DistrictInfo toDistrictInfo(District district);

}