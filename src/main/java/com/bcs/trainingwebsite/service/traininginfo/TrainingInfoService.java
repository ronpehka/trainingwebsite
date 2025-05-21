package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingMapper;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocationRepository;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayMapper;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayRepository;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingInfoService {


    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ProfileRepository profileRepository;
    private final TrainingWeekdayRepository trainingWeekdayRepository;
    private final WeekdayMapper weekdayMapper;
    private final TrainingLocationRepository trainingLocationRepository;
    private final LocationRepository locationRepository;
    private final TrainingWeekdayMapper trainingWeekdayMapper;

    public List<TrainingInfo> getAllTrainingInfo() {
        List<Training> trainings = trainingRepository.findAll();
        List<TrainingInfo> trainingInfos = trainingMapper.toTrainingInfos(trainings);
        addRemainingInformationToTrainingInfos(trainingInfos);
        return trainingInfos;
    }

    private void addRemainingInformationToTrainingInfos(List<TrainingInfo> trainingInfos) {
        for (TrainingInfo trainingInfo : trainingInfos) {
            addCoachFullName(trainingInfo);
            addTrainingDays(trainingInfo);
            handleAddTrainingLocationInfo(trainingInfo);
        }
    }

    private void handleAddTrainingLocationInfo(TrainingInfo trainingInfo) {
        Optional<Location> optionalLocation = trainingLocationRepository.findLocationByTrainingId(trainingInfo.getTrainingId());
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            trainingInfo.setLocationName(location.getName());
            trainingInfo.setAddress(location.getAddress());
        }
    }

    private void addTrainingDays(TrainingInfo trainingInfo) {
        List<TrainingWeekday> trainingWeekdays = trainingWeekdayRepository.findTrainingWeekdaysBy(trainingInfo.getTrainingId());
        List<TrainingDay> trainingDays = trainingWeekdayMapper.toTrainingDays(trainingWeekdays);
        trainingInfo.setTrainingDays(trainingDays);
    }

    private void addCoachFullName(TrainingInfo trainingInfo) {
        Integer coachUserId = trainingInfo.getCoachUserId();
        Profile profile = profileRepository.findProfileBy(coachUserId);
        trainingInfo.setCoachFullName(profile.getFullName());
    }


}
