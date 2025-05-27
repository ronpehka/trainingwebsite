package com.bcs.trainingwebsite.controller.coachinfo;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoachController {


    private final CoachService coachService;

    @GetMapping("/coach-info")
    public List<CoachInfo> findAllCoachesInfo() {
        List<CoachInfo> allCoachesInfo = coachService.findAllCoachesInfo();
        return allCoachesInfo;
    }
}
