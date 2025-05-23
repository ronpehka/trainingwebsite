package com.bcs.trainingwebsite.persistance.user;


import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.login.dto.LoginResponse;
import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = {Status.class})
public interface UserMapper {


    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    LoginResponse toLoginResponse(User user);

    @Mapping(source="email",target = "email")
    @Mapping(source="password",target = "password")
    @Mapping(expression = "java(Status.ACTIVE.getCode())", target = "status")
    User toUser(CustomerProfile customerProfile);

    @InheritConfiguration(name = "toUser")
    User toCoach(CoachProfileDto coachProfileDto);

}