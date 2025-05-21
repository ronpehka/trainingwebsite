package com.bcs.trainingwebsite.persistance.coachimage;

import org.mapstruct.*;
import com.bcs.trainingwebsite.util.ImageConverter;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachImageMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "coachImage",target = "coachImage")
    @Mapping(source = "data",target = "data")
    CoachImage toCoachImage(CoachImage coachImage);

  @Named("toByteArray")
    static byte[] toByteArray(String imageData) {
      return ImageConverter.stringToBytes(imageData);}
}