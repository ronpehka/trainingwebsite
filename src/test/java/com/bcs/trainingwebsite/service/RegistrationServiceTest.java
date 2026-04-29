package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.persistence.coachimage.CoachImageMapper;
import com.bcs.trainingwebsite.persistence.coachimage.CoachImageRepository;
import com.bcs.trainingwebsite.persistence.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.persistence.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistence.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistence.role.Role;
import com.bcs.trainingwebsite.persistence.role.RoleRepository;
import com.bcs.trainingwebsite.persistence.sport.SportRepository;
import com.bcs.trainingwebsite.persistence.user.User;
import com.bcs.trainingwebsite.persistence.user.UserMapper;
import com.bcs.trainingwebsite.persistence.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private RoleRepository roleRepository;
    @Mock private ProfileRepository profileRepository;
    @Mock private ProfileMapper profileMapper;
    @Mock private CoachImageRepository coachImageRepository;
    @Mock private CoachImageMapper coachImageMapper;
    @Mock private CoachSportRepository coachSportRepository;
    @Mock private SportRepository sportRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void addNewCustomer_throwsForbidden_whenEmailAlreadyExists() {
        CustomerProfile profile = new CustomerProfile();
        profile.setEmail("taken@test.ee");
        profile.setPassword("plain");
        when(userRepository.existsByEmail("taken@test.ee")).thenReturn(true);

        assertThatThrownBy(() -> registrationService.addNewCustomer(profile))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(Error.EMAIL_UNAVAILABLE.getMessage());

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void addNewCustomer_persistsUserWithEncodedPassword() {
        CustomerProfile profile = new CustomerProfile();
        profile.setEmail("new@test.ee");
        profile.setPassword("plain");

        Role customerRole = new Role();
        customerRole.setId(RegistrationService.CUSTOMER);
        User mapped = new User();
        mapped.setEmail("new@test.ee");

        when(userRepository.existsByEmail("new@test.ee")).thenReturn(false);
        when(roleRepository.findById(RegistrationService.CUSTOMER)).thenReturn(Optional.of(customerRole));
        when(userMapper.toUser(profile)).thenReturn(mapped);
        when(passwordEncoder.encode("plain")).thenReturn("$2b$10$ENCODED");

        registrationService.addNewCustomer(profile);

        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertThat(savedUser.getValue().getPassword()).isEqualTo("$2b$10$ENCODED");
        assertThat(savedUser.getValue().getRole()).isSameAs(customerRole);
    }
}
