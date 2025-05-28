package com.bcs.trainingwebsite.controller.register;

import com.bcs.trainingwebsite.service.Register.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/customer-training-register")
    public void registerUser(@RequestParam Integer trainingId, @RequestParam Integer userId) {
        registerService.registerUserToTraining(trainingId, userId);
    }
}
