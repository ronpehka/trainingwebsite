package com.bcs.trainingwebsite.persistance.training;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingMapper {


    @Mapping(source = "name", target = "trainingName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    //nädalapäevad, treenerinimi, sporditüüp

    @Mapping(source = "sport.name", target = "sportType")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "maxLimit", target = "maxLimit")
    TrainingInfo toTrainingInfo(Training training);

    List<TrainingInfo> toTrainingInfos(List<Training> trainings);
}