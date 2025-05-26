package com.bcs.trainingwebsite.persistance.weekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekdayMapper {

    @Mapping(source = "id", target="weekdayId")
    @Mapping(source = "shortField", target="weekdayName")
    @Mapping(source = "number", target="weekdayNumber")
    WeekDayInfo toWeekdayInfo(Weekday weekday);

    List<WeekDayInfo> toWeekDayInfos(List<Weekday> weekdays);


}