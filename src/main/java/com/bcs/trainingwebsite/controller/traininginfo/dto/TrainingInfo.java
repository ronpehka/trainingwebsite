package com.bcs.trainingwebsite.controller.traininginfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainingInfo {
    private Integer coachUserId;
    private Integer trainingId;
    private String trainingName;
    private String coachFullName;
    private String trainingDescription;
    private String trainingGender;
    private String startDate;
    private String endDate;
    private List<TrainingDay> trainingDays;
    private String startTime;
    private String endTime;
    private String locationName;//""
    private String address;//""
    private String districtName; //""
    private String sportType;
    private Integer maxLimit;
    private Integer emptyPlaces;

}
