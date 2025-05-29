package com.bcs.trainingwebsite.controller.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.service.traininginfo.TrainingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingInfoController {

    private final TrainingInfoService trainingInfoService;


    @PostMapping("/new-training")
    public Integer addNewTraining(@RequestBody TrainingDto trainingDto) {
       return trainingInfoService.addNewTraining(trainingDto);
    }

    @GetMapping("/training")
    public TrainingDto getTrainingInfo(@RequestParam Integer trainingId){
       return trainingInfoService.getTrainingInfo(trainingId);
    }


    @GetMapping("/training-info")
    public List<TrainingInfo> getTrainingInfos(@RequestParam Integer sportId) {
            return trainingInfoService.getTrainingsBySportIdOrAll(sportId);
        }

    @PutMapping("/training-info")
    public void updateTrainingInfo(@RequestParam Integer trainingId, @RequestBody TrainingDto trainingDto) {
        trainingInfoService.updateTrainingInfo(trainingId, trainingDto);
    }

    @GetMapping("/training-info/by-sport")
    public List<TrainingInfo> getTrainingsBySportName(@RequestParam String sportName) {
        return trainingInfoService.getTrainingsBySportName(sportName);
    }

    @DeleteMapping("/training-info")
    public void removeTraining(@RequestParam Integer trainingId){
        trainingInfoService.removeTraining(trainingId);
    }


}
