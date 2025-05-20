package com.bcs.trainingwebsite.controller.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.service.traininginfo.TrainingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingInfoController {

    private final TrainingInfoService trainingInfoService;

    @GetMapping("/training-info")
    public List<TrainingInfo> getAllTrainingInfo(){
        return trainingInfoService.getAllTrainingInfo();
    }
}
