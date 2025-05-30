package com.bcs.trainingwebsite.controller.sport;


import com.bcs.trainingwebsite.controller.sport.dto.SportInfo;
import com.bcs.trainingwebsite.service.sport.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @GetMapping("/sports")
    public List<SportInfo> getAllSports() {
        return sportService.getAllSports();
    }

}
