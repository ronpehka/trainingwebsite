package com.bcs.trainingwebsite.controller.traininginfo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingDto {
    private Integer coachUserId;
    private Integer sportId;
    private String trainingName;
    private String trainingDescription;
    private String trainingGender;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TrainingWeekdayInfo> trainingDays;
    private String startTime;
    private String endTime;
    private Integer maxLimit;
}
