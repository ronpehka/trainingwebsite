package com.bcs.trainingwebsite.persistance.coachimage;

import org.mapstruct.*;
import com.bcs.trainingwebsite.util.ImageConverter;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachImageMapper {

    @Mapping(source = "user_id",target = "user_id")
    @Mapping(source = "data",target = "data")

    CoachImage toCoachImage(CoachImage coachImage);

  @Named("toByteArray")
    static byte[] toByteArray(String imageData) {
      return ImageConverter.stringToBytes(imageData);}
}