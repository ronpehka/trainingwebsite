package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import org.mapstruct.*;
import com.bcs.trainingwebsite.util.ImageConverter;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachImageMapper {


    @Mapping(source = "imageData",target = "data", qualifiedByName = "toByteArray")
    CoachImage toCoachImage (CoachProfileDto coachProfileDto);

  @Named("toByteArray")
    static byte[] toByteArray(String imageData) {
      return ImageConverter.stringToBytes(imageData);}
}