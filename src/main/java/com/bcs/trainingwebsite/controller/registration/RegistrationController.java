package com.bcs.trainingwebsite.controller.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    @PostMapping("/registration")
    public void addNewCustomer(){

    }
}
