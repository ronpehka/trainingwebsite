package com.bcs.trainingwebsite.persistance.profile;

import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "gender", target = "gender")

    Profile toProfile(CustomerProfile customerProfile);



}