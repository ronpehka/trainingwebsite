package com.bcs.trainingwebsite.controller.traininginfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachTrainingInfo {
    private Integer coachUserId;
    private Integer trainingId;
    private String trainingName;
    private String trainingDescription;
    private String trainingGender;
    private String startDate;
    private String endDate;
    private List<TrainingDayInfo> trainingDays;
    private String startTime;
    private String endTime;
    private String locationName;//""
    private String address;//""
    private String districtName; //""
    private String sportType;
    private Integer maxLimit;
}
