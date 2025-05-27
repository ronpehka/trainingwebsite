package com.bcs.trainingwebsite.persistance.locationimage;

import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.util.ImageConverter;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationImageMapper {
    @Mapping(source = "imageData", target = "data", qualifiedByName = "toByteArray")
    LocationImage toLocationImage(LocationInfo locationInfo);

    @Named("toByteArray")
    static byte[] toByteArray(String imageData) {
        return ImageConverter.stringToBytes(imageData);
    }
}