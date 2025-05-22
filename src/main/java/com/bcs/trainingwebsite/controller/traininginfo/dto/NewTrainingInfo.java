package com.bcs.trainingwebsite.controller.traininginfo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class NewTrainingInfo {
    @NotNull(message = "Treeneri info vajalik")
    private Integer coachUserId;

    private Integer trainingId;

    @NotBlank(message = "Trenni nimi vajalik")
    private String trainingName;

    @NotBlank(message = "Kirjeldust vaja")
    private String trainingDescription;

    @NotBlank(message = "Sugu on vajalik")
    private String trainingGender;

    @NotBlank(message = "Algus kuupäev")
    private String startDate;

    @NotBlank(message = "Hooajalõppu on vaja teada")
    private String endDate;

    @NotEmpty(message = "Vähemalt üks päev peab olema")
    private List<TrainingDayInfo> trainingDays;

    @NotBlank(message = "Algus aeg on vajalik")
    private String startTime;

    @NotBlank(message = "Lõpp o vajalik")
    private String endTime;

    @NotBlank(message = "Asukoha nime vaja")
    private String locationName;

    @NotBlank(message = "Aadressi vaja")
    private String address;

    @NotBlank(message = "Linnnaosa vaja")
    private String districtName;

    @NotBlank(message = "Sporti vaja")
    private String sportType;

    @NotNull(message = "Maksimaalne limiit")
    @Min(value = 1, message = "Minimaalselt saab üks laps käia")
    private Integer maxLimit;
}
