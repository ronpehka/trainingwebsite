package com.bcs.trainingwebsite.service.trainingweekday;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingWeekdayService {

    private final TrainingRepository trainingRepository;
    private final WeekdayRepository weekdayRepository;
    private final WeekdayMapper weekdayMapper;

    public void addTrainingWeekdays(Integer trainingId, List<TrainingWeekdayInfo> trainingWeekdayInfos) {
        Training training = trainingRepository.findTrainingBy(trainingId, Status.ACTIVE.getCode())
                .orElseThrow(() -> new ForeignKeyNotFoundException("trainingId", trainingId));




    }
}
