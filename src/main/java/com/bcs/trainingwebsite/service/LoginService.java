package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.login.dto.LoginResponse;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.security.JwtService;
import com.bcs.trainingwebsite.persistence.user.User;
import com.bcs.trainingwebsite.persistence.user.UserMapper;
import com.bcs.trainingwebsite.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(String email, String password) {
        User user = getValidatedUser(email, password);
        LoginResponse response = userMapper.toLoginResponse(user);
        response.setToken(jwtService.issueToken(user));
        return response;
    }

    private User getValidatedUser(String email, String password) {
        User user = userRepository.findActiveUserByEmail(email, Status.ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(
                        Error.INCORRECT_CREDENTIALS.getMessage(),
                        Error.INCORRECT_CREDENTIALS.getErrorCode()));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ForbiddenException(
                    Error.INCORRECT_CREDENTIALS.getMessage(),
                    Error.INCORRECT_CREDENTIALS.getErrorCode());
        }
        return user;
    }
}
