package com.bcs.trainingwebsite.controller.traininginfo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingDayInfo implements Serializable {
    private Integer weekDayId;
    private String weekDay;
    private Integer weekDayNumber;
    boolean isAvailable;
}
