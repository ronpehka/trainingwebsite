package com.bcs.trainingwebsite.service.trainingweekday;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayRepository;
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingWeekdayService {

    private final TrainingRepository trainingRepository;
    private final WeekdayRepository weekdayRepository;
    private final WeekdayMapper weekdayMapper;
    private final TrainingWeekdayRepository trainingWeekdayRepository;

    public void addTrainingWeekdays(Integer trainingId, List<WeekDayInfo> weekDayInfos) {
        Training training = trainingRepository.findTrainingBy(trainingId, Status.ACTIVE.getCode())
                .orElseThrow(() -> new ForeignKeyNotFoundException("trainingId", trainingId));

        List<TrainingWeekday> trainingWeekdays = weekDayInfos.stream()
                .filter(WeekDayInfo::isAvailable)
                .map(weekDayInfo -> {
                    Weekday weekday = weekdayRepository.findById(weekDayInfo.getWeekdayId())
                            .orElseThrow(() -> new ForeignKeyNotFoundException("weekdayId", weekDayInfo.getWeekdayId()));
                    TrainingWeekday trainingWeekday = new TrainingWeekday();
                    trainingWeekday.setTraining(training);
                    trainingWeekday.setWeekday(weekday);
                    return trainingWeekday;
                })
                .collect(Collectors.toList());

        trainingWeekdayRepository.saveAll(trainingWeekdays);
    }

}
