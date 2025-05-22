package com.bcs.trainingwebsite.persistance.weekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDayInfo;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekdayMapper {
    @Mapping(source = "shortField", target = "weekday")
    TrainingDay toWeekdayDto(Weekday weekday);


}