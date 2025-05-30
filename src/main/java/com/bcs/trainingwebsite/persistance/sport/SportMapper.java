package com.bcs.trainingwebsite.persistance.sport;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachSportInfo;
import com.bcs.trainingwebsite.controller.sport.dto.SportInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SportMapper {

    @Mapping(source = "name",target = "sportType")
    CoachSportInfo toCoachSport(Sport sport);
    List<CoachSportInfo> toCoachSportInfos(List<Sport>sports);

    @Mapping(source = "id", target = "sportId")
    @Mapping(source = "name", target = "sportName")
    @Mapping(constant = "false", target = "available")
    SportInfo toSportInfo(Sport sport);

    List<SportInfo> toSportInfos(List<Sport>sports);


}