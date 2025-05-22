package com.bcs.trainingwebsite.controller.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.NewTrainingInfo;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.service.traininginfo.TrainingInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingInfoController {

    private final TrainingInfoService trainingInfoService;


   @PostMapping("/training-info")
   public void addNewTraining(@RequestBody @Valid NewTrainingInfo newTrainingInfo){
       trainingInfoService.addNewTraining(newTrainingInfo);
   }

    @GetMapping("/training-info")
    public List<TrainingInfo> getAllTrainingInfo(){
        return trainingInfoService.getAllTrainingInfo();
    }
}
