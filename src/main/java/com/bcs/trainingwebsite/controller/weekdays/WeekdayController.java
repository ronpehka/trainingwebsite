package com.bcs.trainingwebsite.controller.weekdays;


import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import com.bcs.trainingwebsite.service.weekday.WeekDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeekdayController {

    private final WeekDayService weekDayService;

    @GetMapping("/weekdays")
    public List<WeekDayInfo> getAllWeekDays(){
        return weekDayService.getAllWeekDays();
    }
}
