package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.controller.traininginfo.dto.WeekdayDto;
import com.bcs.trainingwebsite.infrastructure.exception.DataNotFoundException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingMapper;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocationRepository;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayRepository;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TrainingInfo> getAllTrainingInfo() {
        List<Training> trainings = trainingRepository.findAll();
        List<TrainingInfo> trainingInfos = new ArrayList<>();
        getCorrectTrainingInfo(trainings, trainingInfos);
        return trainingInfos;
    }

    private void getCorrectTrainingInfo(List<Training> trainings, List<TrainingInfo> trainingInfos) {
        for (Training training : trainings) {
            TrainingInfo trainingInfo = trainingMapper.toTrainingInfo(training);
            getCoachName(training, trainingInfo);
            getWeekDays(training, trainingInfo);
            getLocationInfo(training, trainingInfo);
            trainingInfos.add(trainingInfo);
        }
    }

    private void getLocationInfo(Training training, TrainingInfo trainingInfo) {
        Integer locationId = getLocationId(training);
        String name = locationRepository.getReferenceById(locationId).getName();
        String address = locationRepository.getReferenceById(locationId).getAddress();
        trainingInfo.setLocationName(name);
        trainingInfo.setAddress(address);
    }

    private Integer getLocationId(Training training) {
        return trainingLocationRepository.findLocationByTrainingId(training.getId()).orElseThrow(() -> new DataNotFoundException("trainingId", training.getId()));
    }

    private void getWeekDays(Training training, TrainingInfo trainingInfo) {
        List<TrainingWeekday> trainingWeekdays = trainingWeekdayRepository.findTrainingWeekdayBy(training.getId());
        List<WeekdayDto> weekdayDtos = trainingWeekdays.stream()
                .map(tw -> weekdayMapper.toWeekdayDto(tw.getWeekday())).toList();
        trainingInfo.setWeekdays(weekdayDtos);
    }

    private void getCoachName(Training training, TrainingInfo trainingInfo) {
        Profile profile = getCorrectCoach(training);
        trainingInfo.setCoachName(profile.getFirstName() + " " + profile.getLastName());
    }

    private Profile getCorrectCoach(Training training) {
        return profileRepository.findById(training.getCoachUser()
                .getId()).orElseThrow(() -> new ForeignKeyNotFoundException("CoachUser", training.getCoachUser().getId()));
    }
}
