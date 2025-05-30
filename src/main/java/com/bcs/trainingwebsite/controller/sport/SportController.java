package com.bcs.trainingwebsite.controller.sport;


import com.bcs.trainingwebsite.controller.sport.dto.SportInfo;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.service.sport.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;
    private final CoachSportRepository coachSportRepository;

    @GetMapping("/sports")
    public List<SportInfo> getAllSports(){
       return sportService.getAllSports();
    }




}
