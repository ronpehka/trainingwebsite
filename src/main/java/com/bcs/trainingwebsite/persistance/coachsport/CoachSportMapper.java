package com.bcs.trainingwebsite.persistance.coachsport;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.persistance.sport.Sport;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachSportMapper {


//    @Mapping(source = "sports.sportType", target = "sport")
//    CoachSport toCoachSport(CoachInfo coachInfo);

//
//    @Mapping(source = "sport", target="sports.sportType")
//    CoachInfo toCoachInfo(CoachSport coachSport);

//    List <CoachInfo> toCoachInfos(List<Sport>sports);

}