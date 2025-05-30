package com.bcs.trainingwebsite.persistance.district;

import com.bcs.trainingwebsite.controller.location.dto.DistrictDto;
import com.bcs.trainingwebsite.controller.location.dto.DistrictInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DistrictMapper {

    @Mapping(source = "id", target = "districtId")
    @Mapping(source = "name", target = "districtName")
    DistrictInfo toDistrictInfo(District district);
    List<DistrictInfo> toDistrictInfos(List<District> districts);

    @Mapping(source = "name", target = "name")
    District toDistrict(DistrictDto districtDto);

}