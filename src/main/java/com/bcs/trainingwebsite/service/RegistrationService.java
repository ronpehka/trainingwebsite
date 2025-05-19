package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.DataNotFoundException;
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

import java.util.Optional;

import static com.bcs.trainingwebsite.infrastructure.error.Error.EMAIL_UNAVAILABLE;

@Service
@RequiredArgsConstructor
public class RegistrationService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public void addNewCustomer(CustomerProfile customerProfile) {
        boolean userExists = userRepository.existsByEmail(customerProfile.getEmail());
        if (userExists) {
            throw new ForbiddenException(EMAIL_UNAVAILABLE.getMessage(), EMAIL_UNAVAILABLE.getErrorCode());
        }
        String password = customerProfile.getPassword();
        User user = userMapper.toUser(customerProfile);
        Role role = roleRepository.findById(3).orElseThrow(() -> new DataNotFoundException(Error.NO_ATM_LOCATIONS_FOUND.getMessage(), Error.NO_ATM_LOCATIONS_FOUND.getErrorCode()));
        user.setRole(role);
        user.setPassword(password);
        userRepository.save(user);
        Profile profile = profileMapper.toProfile(customerProfile);
        profileRepository.save(profile);

    }
}
