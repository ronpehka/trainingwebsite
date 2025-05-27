package com.bcs.trainingwebsite.persistance.sport;

import com.bcs.trainingwebsite.controller.coachinfo.dto.SportInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface SportMapper {

    @Mapping(source = "name",target = "sportType")
    SportInfo toSportInfo(Sport sport);

    List<SportInfo> toSportInfos(List<Sport>sports);


}