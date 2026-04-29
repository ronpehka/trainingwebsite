package com.bcs.trainingwebsite.controller.login;


import com.bcs.trainingwebsite.controller.login.dto.LoginRequest;
import com.bcs.trainingwebsite.controller.login.dto.LoginResponse;
import com.bcs.trainingwebsite.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse logIn(@Valid @RequestBody LoginRequest request) {
        return loginService.login(request.getEmail(), request.getPassword());
    }
}
