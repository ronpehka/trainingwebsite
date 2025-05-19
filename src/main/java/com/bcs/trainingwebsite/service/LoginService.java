package com.bcs.trainingwebsite.service;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.login.dto.LoginResponse;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.persistance.user.User;
import com.bcs.trainingwebsite.persistance.user.UserMapper;
import com.bcs.trainingwebsite.persistance.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public LoginResponse login(String email, String password) {
        User user = getValidatedUser(email, password);
        return userMapper.toLoginResponse(user);

    }

    private User getValidatedUser(String email, String password) {
        User user = userRepository.findUserBy(email, password, Status.ACTIVE.getCode()).orElseThrow(
                () -> new ForbiddenException(Error.INCORRECT_CREDENTIALS.getMessage(), Error.INCORRECT_CREDENTIALS.getErrorCode()));
        return user;
    }


}
