package com.bcs.trainingwebsite.persistance.training;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.util.DateConverter;
import com.bcs.trainingwebsite.util.TimeConverter;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingMapper {


    @Mapping(source = "name", target = "trainingName")
    @Mapping(source = "description", target = "trainingDescription")
    @Mapping(source = "gender", target = "trainingGender")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "toFormattedStartDate")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName =  "toFormattedEndDate")
    //nädalapäevad, treenerinimi, sporditüüp

    @Mapping(source = "id", target = "trainingId")
    @Mapping(source = "coachUser.id", target = "coachUserId")
    @Mapping(source = "sport.name", target = "sportType")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toFormattedStartTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toFormattedEndTime")
    @Mapping(source = "maxLimit", target = "maxLimit")
    @Mapping(constant = "", target = "address")
    @Mapping(constant = "", target = "locationName")
    TrainingInfo toTrainingInfo(Training training);

    List<TrainingInfo> toTrainingInfos(List<Training> trainings);

    @Named("toFormattedStartDate")
    static String toStringStartDate(LocalDate startDate){
        return DateConverter.localDateToString(startDate);
    }
    @Named("toFormattedEndDate")
    static String toStringEndDate(LocalDate endDate){
        return DateConverter.localDateToString(endDate);
    }
    @Named("toFormattedStartTime")
    static String toStringStartTime(LocalTime startTime){
        return TimeConverter.localTimeToString(startTime);
    }
    @Named("toFormattedEndTime")
    static String toStringEndTime(LocalTime endTime){
        return TimeConverter.localTimeToString(endTime);
    }

}