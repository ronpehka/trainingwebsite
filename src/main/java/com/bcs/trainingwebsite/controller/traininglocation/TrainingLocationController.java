package com.bcs.trainingwebsite.controller.traininglocation;

import com.bcs.trainingwebsite.service.traininglocation.TrainingLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TrainingLocationController {

    private final TrainingLocationService trainingLocationService;

    @PostMapping("/training-location")
    public void addTrainingLocation(@RequestParam Integer trainingId, @RequestParam Integer locationId){
        trainingLocationService.addTrainingLocation(trainingId, locationId);
    }
}
