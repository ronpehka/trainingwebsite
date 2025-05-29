package com.bcs.trainingwebsite.persistance.trainingweekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingWeekdayMapper {
    @Mapping(source = "weekday.shortField", target = "weekday")
    TrainingDay toTrainingDay(TrainingWeekday trainingWeekday);

    List<TrainingDay> toTrainingDays(List<TrainingWeekday> trainingWeekdays);

    @Mapping(source = "weekday", target="weekday.shortField")
    TrainingWeekday toTrainingWeekday(TrainingDay trainingDay);
    List<TrainingWeekday> toTrainingWeekDays(List<TrainingDay> trainingDays);

    @Mapping(source = "weekday.id", target="weekdayId")
    @Mapping(source = "weekday.shortField", target = "weekdayName")
    @Mapping(source = "weekday.number", target = "weekdayNumber")
    @Mapping(constant="true", target = "available")
    TrainingWeekdayInfo toTrainingWeekdayInfo(TrainingWeekday trainingWeekday);
    List<TrainingWeekdayInfo> toTrainingWeekdayInfos(List<TrainingWeekday> trainingWeekdays);



}