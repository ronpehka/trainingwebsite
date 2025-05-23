package com.bcs.trainingwebsite.controller.traininginfo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingWeekdayInfo implements Serializable {
    private Integer weekdayId;
    private String weekdayName;
    private Integer weekdayNumber;
    boolean isAvailable;
}
