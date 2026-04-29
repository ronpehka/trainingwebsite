package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.controller.login.dto.LoginResponse;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.security.JwtService;
import com.bcs.trainingwebsite.persistence.user.User;
import com.bcs.trainingwebsite.persistence.user.UserMapper;
import com.bcs.trainingwebsite.persistence.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("user@test.ee");
        user.setPassword("$2b$10$hashedhashedhashedhashedhashedhashedhashedhashedhashedhash");
        user.setStatus("A");
    }

    @Test
    void login_returnsResponseWithToken_whenCredentialsValid() {
        LoginResponse mapped = new LoginResponse();
        when(userRepository.findActiveUserByEmail("user@test.ee", "A")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("plaintext", user.getPassword())).thenReturn(true);
        when(userMapper.toLoginResponse(user)).thenReturn(mapped);
        when(jwtService.issueToken(user)).thenReturn("signed.jwt.token");

        LoginResponse actual = loginService.login("user@test.ee", "plaintext");

        assertThat(actual).isSameAs(mapped);
        assertThat(actual.getToken()).isEqualTo("signed.jwt.token");
    }

    @Test
    void login_throwsForbidden_whenUserNotFound() {
        when(userRepository.findActiveUserByEmail("ghost@test.ee", "A")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginService.login("ghost@test.ee", "anything"))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(Error.INCORRECT_CREDENTIALS.getMessage())
                .extracting("errorCode").isEqualTo(Error.INCORRECT_CREDENTIALS.getErrorCode());

        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).issueToken(any());
    }

    @Test
    void login_throwsForbidden_whenPasswordDoesNotMatch() {
        when(userRepository.findActiveUserByEmail("user@test.ee", "A")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("wrong"), any())).thenReturn(false);

        assertThatThrownBy(() -> loginService.login("user@test.ee", "wrong"))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(Error.INCORRECT_CREDENTIALS.getMessage());

        verify(userMapper, never()).toLoginResponse(any());
        verify(jwtService, never()).issueToken(any());
    }
}
