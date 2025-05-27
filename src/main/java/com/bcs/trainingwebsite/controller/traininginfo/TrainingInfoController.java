package com.bcs.trainingwebsite.controller.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.service.traininginfo.TrainingInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingInfoController {

    private final TrainingInfoService trainingInfoService;


    @PostMapping("/add-new-training")
    public void addNewTraining(@RequestBody TrainingDto trainingDto) {
        trainingInfoService.addNewTraining(trainingDto);
    }

    @GetMapping("/training-info")
    public List<TrainingInfo> getAllTrainingInfo() {
        return trainingInfoService.getAllTrainingInfo();
    }

    @PutMapping("/training-info")
    public void updateTrainingInfo(@RequestParam Integer trainingId, @RequestBody TrainingDto trainingDto) {
        trainingInfoService.updateTrainingInfo(trainingId, trainingDto);
    }

    @GetMapping("/training-info/by-sport")
    public List<TrainingInfo> getTrainingsBySportName(@RequestParam String sportName) {
        return trainingInfoService.getTrainingsBySportName(sportName);
    }

}
