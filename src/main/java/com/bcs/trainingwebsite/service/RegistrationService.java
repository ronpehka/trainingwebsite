package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistence.coachimage.CoachImage;
import com.bcs.trainingwebsite.persistence.coachimage.CoachImageMapper;
import com.bcs.trainingwebsite.persistence.coachimage.CoachImageRepository;
import com.bcs.trainingwebsite.persistence.coachsport.CoachSport;
import com.bcs.trainingwebsite.persistence.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.persistence.profile.Profile;
import com.bcs.trainingwebsite.persistence.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistence.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistence.role.Role;
import com.bcs.trainingwebsite.persistence.role.RoleRepository;
import com.bcs.trainingwebsite.persistence.sport.Sport;
import com.bcs.trainingwebsite.persistence.sport.SportRepository;
import com.bcs.trainingwebsite.persistence.user.User;
import com.bcs.trainingwebsite.persistence.user.UserMapper;
import com.bcs.trainingwebsite.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final CoachSportRepository coachSportRepository;
    private final SportRepository sportRepository;
    private final PasswordEncoder passwordEncoder;


    public void addNewCustomer(CustomerProfile customerProfile) {
        validateEmailIsAvailable(customerProfile.getEmail());
        User user = createAndSaveUser(customerProfile);
        createAndSaveProfile(customerProfile, user);
    }

    public Integer addNewCoach(CoachProfileDto coachProfileDto) {
        validateEmailIsAvailable(coachProfileDto.getEmail());
        User user = createAndSaveUser(coachProfileDto);
        createAndSaveCoachProfile(coachProfileDto, user);
        return user.getId();
    }

    public void addCoachSports(Integer userId, List<Integer> sportIds) {
        User user = getValidUser(userId);
        for (Integer sportId : sportIds) {
            Sport sport = getValidSport(sportId);
            CoachSport coachSport = createCoachSport(user, sport);
            coachSportRepository.save(coachSport);
        }

    }

    private static CoachSport createCoachSport(User user, Sport sport) {

        CoachSport coachSport = new CoachSport();
        coachSport.setCoachUser(user);
        coachSport.setSport(sport);
        return coachSport;
    }

    private Sport getValidSport(Integer sportId) {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("sportId", sportId));
        return sport;
    }

    private User getValidUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));
        return user;
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
        user.setPassword(passwordEncoder.encode(customerProfile.getPassword()));
        userRepository.save(user);
        return user;
    }

    private User createAndSaveUser(CoachProfileDto coachProfileDto) {
        Role roleCoach = roleRepository.findById(COACH).get();
        User user = userMapper.toCoach(coachProfileDto);
        user.setRole(roleCoach);
        user.setPassword(passwordEncoder.encode(coachProfileDto.getPassword()));
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


