package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.util.ImageConverter;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachImageMapper {


    @Mapping(source = "imageData", target = "data", qualifiedByName = "toByteArray")
    CoachImage toCoachImage(CoachProfileDto coachProfileDto);

    @Mapping(source = "imageData", target = "data", qualifiedByName = "toByteArray")
    CoachImage toCoachImage2 (CoachInfo coachInfo);

//
//    @Mapping(source = "data", target = "imageData", qualifiedByName = "toArrayByte")
//    CoachInfo toCoachInfo (CoachImage coachImage);


    @Named("toByteArray")
    static byte[] toByteArray(String imageData) {
        return ImageConverter.stringToBytes(imageData);
    }

//    @Named("toArrayByte")
//    static byte[] toArrayByte(String data) {
//        return ImageConverter.bytesToString(data);
//    }
}