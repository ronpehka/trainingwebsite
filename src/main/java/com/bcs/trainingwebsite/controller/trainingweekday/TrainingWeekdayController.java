package com.bcs.trainingwebsite.controller.trainingweekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import com.bcs.trainingwebsite.service.trainingweekday.TrainingWeekdayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingWeekdayController {
    private final TrainingWeekdayService trainingWeekdayService;

    @PostMapping("/training-weekdays")
    public void addTrainingWeekdays(@RequestParam Integer trainingId, @RequestBody List<WeekDayInfo> weekDayInfos) {
        trainingWeekdayService.addTrainingWeekdays(trainingId, weekDayInfos);
    }
}
