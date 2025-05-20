package com.bcs.trainingwebsite.controller.traininginfo.dto;

import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TrainingInfo {
    private String trainingName;
    private String coachName;
    private String description;
    private String gender;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<WeekdayDto> weekdays;
    private LocalTime startTime;
    private LocalTime endTime;
    private String sportType;
    private Integer maxLimit;

}
