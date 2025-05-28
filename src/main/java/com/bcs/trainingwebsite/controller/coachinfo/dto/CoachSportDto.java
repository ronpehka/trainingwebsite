package com.bcs.trainingwebsite.controller.coachinfo.dto;

import com.bcs.trainingwebsite.persistance.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoachSportDto {

    @NotNull
    private User coachUser;

}
