package com.bcs.trainingwebsite.controller.sport.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachSportDto {

    private Integer userId;
    private List<Integer> sportIds;
}
