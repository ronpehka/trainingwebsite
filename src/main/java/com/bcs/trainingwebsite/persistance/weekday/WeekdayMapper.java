package com.bcs.trainingwebsite.persistance.weekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.WeekdayDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekdayMapper {
    @Mapping(source = "shortField", target = "weekday")
    WeekdayDto toWeekdayDto(Weekday weekday);

}