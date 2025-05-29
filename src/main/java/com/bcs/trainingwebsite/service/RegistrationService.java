package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImage;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageMapper;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageRepository;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSport;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.role.Role;
import com.bcs.trainingwebsite.persistance.role.RoleRepository;
import com.bcs.trainingwebsite.persistance.sport.Sport;
import com.bcs.trainingwebsite.persistance.sport.SportRepository;
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
    private final CoachSportRepository coachSportRepository;
    private final SportRepository sportRepository;


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

    public void addCoachSport(Integer userId, Integer sportId) {
        User user = getValidUser(userId);
        Sport sport = getValidSport(sportId);
        CoachSport coachSport = createCoachSport(user, sport);
        coachSportRepository.save(coachSport);
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


