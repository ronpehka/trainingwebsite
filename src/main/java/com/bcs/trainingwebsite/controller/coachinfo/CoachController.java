package com.bcs.trainingwebsite.controller.coachinfo;

import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.service.CoachService;
import com.bcs.trainingwebsite.service.RegistrationService;
import com.bcs.trainingwebsite.service.sport.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoachController {


    private final CoachService coachService;
    private final SportService sportService;
    private final CoachSportRepository coachSportRepository;
    private final RegistrationService registrationService;

    @GetMapping("/coach-info")
    public List<CoachInfo> findAllCoachesInfo() {
        List<CoachInfo> allCoachesInfo = coachService.findAllCoachesInfo();
        return allCoachesInfo;
    }


    @PostMapping("/coach-sport")
    public void addCoachSport(@RequestParam Integer userId, @RequestParam Integer sportId) {
        registrationService.addCoachSport(userId, sportId);

    }

}

