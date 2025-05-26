package com.bcs.trainingwebsite.persistance.profile;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "gender", target = "gender")
    Profile toProfile(CustomerProfile customerProfile);


    @InheritConfiguration(name = "toProfile")
    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "description", target = "description")
    Profile toProfile(CoachProfileDto coachProfileDto);

    @Mapping(source = "user.id", target = "coachUserId")
    @Mapping(expression = "java(profile.getFullName())", target = "coachFullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(constant = "", target = "imageData")
    CoachInfo toCoachInfo(Profile profile);




    List<CoachInfo> toCoachInfos(List<Profile> profiles);


}