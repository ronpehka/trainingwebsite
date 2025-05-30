package com.bcs.trainingwebsite.controller.registration;

import com.bcs.trainingwebsite.controller.registration.dto.CoachProfileDto;
import com.bcs.trainingwebsite.controller.registration.dto.CustomerProfile;
import com.bcs.trainingwebsite.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/customer-registration")
    public void addNewCustomer(@RequestBody CustomerProfile customerProfile) {
        registrationService.addNewCustomer(customerProfile);

    }

    @PostMapping("/coach-registration")
    public Integer addNewCoach(@RequestBody CoachProfileDto coachProfileDto) {
        return registrationService.addNewCoach(coachProfileDto);
    }

}