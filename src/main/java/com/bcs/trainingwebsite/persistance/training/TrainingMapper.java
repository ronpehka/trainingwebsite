package com.bcs.trainingwebsite.persistance.training;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.persistance.sport.SportMapper;
import com.bcs.trainingwebsite.util.DateConverter;
import com.bcs.trainingwebsite.util.TimeConverter;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = {Status.class}, uses = {SportMapper.class})
public interface TrainingMapper {


    @Mapping(source = "name", target = "trainingName")
    @Mapping(source = "description", target = "trainingDescription")
    @Mapping(source = "gender", target = "trainingGender")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "toFormattedStartDate")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "toFormattedEndDate")
    @Mapping(source = "id", target = "trainingId")
    @Mapping(source = "coachUser.id", target = "coachUserId")
    @Mapping(source = "sport.name", target = "sportType")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toFormattedStartTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toFormattedEndTime")
    @Mapping(source = "maxLimit", target = "maxLimit")
    @Mapping(constant = "", target = "address")
    @Mapping(constant = "", target = "locationName")
    @Mapping(constant = "", target = "districtName")
    @Mapping(target = "emptyPlaces", ignore = true)
    TrainingInfo toTrainingInfo(Training training);

    List<TrainingInfo> toTrainingInfos(List<Training> trainings);

    @Mapping(source = "trainingName", target = "name")
    @Mapping(source = "trainingDescription", target = "description")
    @Mapping(source = "trainingGender", target = "gender")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toDatabaseTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toDatabaseTime")
    @Mapping(source = "maxLimit", target = "maxLimit")
    @Mapping(expression = "java(Status.ACTIVE.getCode())", target = "status")
    Training toTraining(TrainingDto trainingDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "trainingName", target = "name")
    @Mapping(source = "trainingDescription", target = "description")
    @Mapping(source = "trainingGender", target = "gender")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toDatabaseTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toDatabaseTime")
    @Mapping(source = "maxLimit", target = "maxLimit")
    Training partialUpdate(@MappingTarget Training training, TrainingDto trainingDto);

    @Named("toFormattedStartDate")
    static String toStringStartDate(LocalDate startDate) {
        return DateConverter.localDateToString(startDate);
    }

    @Named("toFormattedEndDate")
    static String toStringEndDate(LocalDate endDate) {
        return DateConverter.localDateToString(endDate);
    }

    @Named("toFormattedStartTime")
    static String toStringStartTime(LocalTime startTime) {
        return TimeConverter.localTimeToString(startTime);
    }

    @Named("toFormattedEndTime")
    static String toStringEndTime(LocalTime endTime) {
        return TimeConverter.localTimeToString(endTime);
    }

    @Named("toDatabaseTime")
    static LocalTime toLocalTimeStartTime(String time) {
        return TimeConverter.stringToLocalTime(time);
    }

}