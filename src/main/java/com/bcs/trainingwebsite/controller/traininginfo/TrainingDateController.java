package com.bcs.trainingwebsite.controller.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDateDto;
import com.bcs.trainingwebsite.service.trainingdate.TrainingDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingDateController {
    private final TrainingDateService trainingDateService;

    @GetMapping("/training-dates")
    public List<TrainingDateDto> getAllTrainingDates() {
        return trainingDateService.getAllTrainingDates();
    }
}
