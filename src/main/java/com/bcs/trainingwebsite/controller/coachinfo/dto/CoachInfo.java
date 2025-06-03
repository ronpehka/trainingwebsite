package com.bcs.trainingwebsite.controller.coachinfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachInfo {
    private Integer coachUserId;
    private String coachFullName;
    private String email;
    private String phoneNumber;
    private List<CoachSportInfo> sports;
    private String description;
    private String imageData;
}
