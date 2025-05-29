package com.bcs.trainingwebsite.persistance.trainingdate;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDateDto;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingDateMapper {

    @Mapping(source = "date", target = "trainingDate")
    @Mapping(source = "training.id", target = "trainingId")
    TrainingDateDto toTrainingDateDto(TrainingDate trainingDate);

    List<TrainingDateDto> toTrainingDateDtos(List<TrainingDate> trainingDates);

    @Named("localDateToString")
    static String localDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }
}
