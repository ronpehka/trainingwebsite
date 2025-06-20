package com.bcs.trainingwebsite.persistance.weekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekdayMapper {

    @Mapping(source = "id", target = "weekdayId")
    @Mapping(source = "shortField", target = "weekdayName")
    @Mapping(source = "number", target = "weekdayNumber")
    @Mapping(constant = "false", target="available")
    WeekDayInfo toWeekDayInfo(Weekday weekday);

    List<WeekDayInfo> toWeekDayInfos(List<Weekday> weekdays);

    @Mapping(source = "weekdayName", target = "shortField")
    @Mapping(source="weekdayNumber", target = "number")
    Weekday toWeekday(WeekDayInfo weekDayInfo);

}