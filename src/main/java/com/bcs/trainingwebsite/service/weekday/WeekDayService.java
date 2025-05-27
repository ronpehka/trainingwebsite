package com.bcs.trainingwebsite.service.weekday;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.controller.weekdays.dto.WeekDayInfo;
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeekDayService {
    private final WeekdayRepository weekdayRepository;
    private final WeekdayMapper weekdayMapper;


    public List<WeekDayInfo> getAllWeekDays() {

        List<Weekday> weekdays = weekdayRepository.findAll();
        return weekdayMapper.toWeekDayInfos(weekdays);
    }
}
