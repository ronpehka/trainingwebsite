package com.bcs.trainingwebsite.persistance.profile;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfile;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

//    private String firstName;
//    private String lastName;
//    private String email;
//    private String phoneNumber;
//    private LocalDate dateOfBirth;
//    private String gender;

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "gender", target = "gender")
    Profile toProfile(CustomerProfile customerProfile);


    @InheritConfiguration(name = "toProfile")
    @Mapping(source = "phoneNumber",target = "phone")
    @Mapping(source = "description",target = "description")
    Profile toCoachProfile(CoachProfile coachProfile);


}