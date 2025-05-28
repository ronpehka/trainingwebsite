package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImage;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageMapper;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.role.Role;
import com.bcs.trainingwebsite.persistance.role.RoleRepository;
import com.bcs.trainingwebsite.persistance.user.User;
import com.bcs.trainingwebsite.persistance.user.UserMapper;
import com.bcs.trainingwebsite.persistance.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bcs.trainingwebsite.infrastructure.error.Error.EMAIL_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class RegistrationService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    public static final int CUSTOMER = 3;
    private static final int COACH = 2;
    private final CoachImageRepository coachImageRepository;
    private final CoachImageMapper coachImageMapper;


    public void addNewCustomer(CustomerProfile customerProfile) {
        validateEmailIsAvailable(customerProfile.getEmail());
        User user = createAndSaveUser(customerProfile);
        createAndSaveProfile(customerProfile, user);
    }

    public void addNewCoach(CoachProfileDto coachProfileDto) {
        validateEmailIsAvailable(coachProfileDto.getEmail());
        User user = createAndSaveUser(coachProfileDto);
        createAndSaveCoachProfile(coachProfileDto, user);
    }

    private void createAndSaveProfile(CustomerProfile customerProfile, User user) {
        Profile profile = profileMapper.toProfile(customerProfile);
        profile.setUser(user);
        profileRepository.save(profile);
    }

    private void createAndSaveCoachProfile(CoachProfileDto coachProfileDto, User user) {
        Profile profile = profileMapper.toProfile(coachProfileDto);
        profile.setUser(user);
        profileRepository.save(profile);
    }

    private User createAndSaveUser(CustomerProfile customerProfile) {
        Role roleCustomer = roleRepository.findById(CUSTOMER).get();
        User user = userMapper.toUser(customerProfile);
        user.setRole(roleCustomer);
        userRepository.save(user);
        return user;
    }

    private User createAndSaveUser(CoachProfileDto coachProfileDto) {
        Role roleCoach = roleRepository.findById(COACH).get();
        User user = userMapper.toCoach(coachProfileDto);
        user.setRole(roleCoach);
        userRepository.save(user);
        handleAddCoachImageData(user, coachProfileDto);
        return user;
    }

    private void validateEmailIsAvailable(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        if (userExists) {
            throw new ForbiddenException(EMAIL_UNAVAILABLE.getMessage(), EMAIL_UNAVAILABLE.getErrorCode());
        }
    }


    private void handleAddCoachImageData(User user, CoachProfileDto coachProfileDto) {

        CoachImage coachImage = coachImageMapper.toCoachImage(coachProfileDto);
        coachImage.setUser(user);
        coachImageRepository.save(coachImage);


    }


}


