package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfile;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
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

    public void addNewCustomer(CustomerProfile customerProfile) {
        validateEmailIsAvailable(customerProfile.getEmail());
        User user = createAndSaveUser(customerProfile);
        createAndSaveProfile(customerProfile, user);
    }

    public void addNewCoach(CoachProfile coachProfile) {
        validateEmailIsAvailable(coachProfile.getEmail());
        User user = createAndSaveUser(coachProfile);
        createAndSaveCoachProfile(coachProfile, user);
    }

    private void createAndSaveProfile(CustomerProfile customerProfile, User user) {
        Profile profile = profileMapper.toProfile(customerProfile);
        profile.setUser(user);
        profileRepository.save(profile);
    }

    private void createAndSaveCoachProfile(CoachProfile coachProfile, User user) {
        Profile profile = profileMapper.toCoachProfile(coachProfile);
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

    private User createAndSaveUser(CoachProfile coachProfile) {
        Role roleCoach = roleRepository.findById(COACH).get();
        User user = userMapper.toUser(coachProfile);
        user.setRole(roleCoach);
        userRepository.save(user);
        return user;
    }

    private void validateEmailIsAvailable(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        if (userExists) {
            throw new ForbiddenException(EMAIL_UNAVAILABLE.getMessage(), EMAIL_UNAVAILABLE.getErrorCode());
        }
    }
}
