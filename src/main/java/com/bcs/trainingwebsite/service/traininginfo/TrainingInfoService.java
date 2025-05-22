package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.persistance.district.District;
import com.bcs.trainingwebsite.persistance.district.DistrictRepository;
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
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayRepository;
import com.bcs.trainingwebsite.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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
    private final DistrictRepository districtRepository;
    private final WeekdayRepository weekdayRepository;

    public List<TrainingInfo> getAllTrainingInfo() {
        List<Training> trainings = trainingRepository.findTrainingsBy(Status.ACTIVE.getCode());
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
            trainingInfo.setDistrictName(location.getDistrict().getName());
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

    @Transactional
    public void addNewTraining(TrainingInfo trainingInfo) {
        Location location = locationRepository.findLocationBy(trainingInfo.getLocationName(), trainingInfo.getAddress())
                .orElseGet(() -> {
                    Location newLocation = new Location();
                    newLocation.setAddress(trainingInfo.getAddress());
                    newLocation.setName(trainingInfo.getLocationName());
                    District district = districtRepository.findDistrictBy(trainingInfo.getDistrictName()).orElseGet(() -> {
                        District newDistrict = new District();
                        newDistrict.setName(trainingInfo.getDistrictName());
                        return districtRepository.save(newDistrict);
                    });
                    newLocation.setDistrict(district);
                    return locationRepository.save(newLocation);

                });
        handleAddTrainingLocationInfo(trainingInfo);
        LocalTime newTrainingStartTime = TimeConverter.stringToLocalTime(trainingInfo.getStartTime());
        LocalTime newTrainingEndTime = TimeConverter.stringToLocalTime(trainingInfo.getEndTime());
        List<Training> existingTrainings = trainingRepository.existingTrainings(trainingInfo.getCoachUserId(), Status.ACTIVE.getCode());
        List<TrainingInfo> existingTrainingInfos = trainingMapper.toTrainingInfos(existingTrainings);
       for (TrainingInfo existingTrainingInfo : existingTrainingInfos) {
            addTrainingDays(existingTrainingInfo);
           handleAddTrainingLocationInfo(existingTrainingInfo);
           if(existingTrainingInfo.equals(trainingInfo)){
               throw new ForbiddenException("Selline trenn on juba olemas", 333);
           }
           List<TrainingWeekday> trainingWeekdays = trainingWeekdayRepository.findTrainingWeekdaysBy(existingTrainingInfo.getTrainingId());
           List<TrainingDay> currentTrainingDays = trainingWeekdayMapper.toTrainingDays(trainingWeekdays);
           for (TrainingDay newTrainingDay : trainingInfo.getTrainingDays()) {
               for (TrainingDay existingWeekDay : currentTrainingDays) {
                   if (!existingWeekDay.getWeekday().equals(newTrainingDay.getWeekday())) continue;
                   LocalTime existingStartTime = TimeConverter.stringToLocalTime(existingTrainingInfo.getStartTime());
                   LocalTime existingEndTime = TimeConverter.stringToLocalTime(existingTrainingInfo.getEndTime());
                   boolean sameTime = existingStartTime.equals(newTrainingStartTime) && existingEndTime.equals(newTrainingEndTime);
                   boolean differentLocation = !existingTrainingInfo.getLocationName().equals(trainingInfo.getLocationName()) || !existingTrainingInfo.getAddress().equals(trainingInfo.getAddress());
                   if (sameTime && differentLocation) {
                       throw new IllegalArgumentException("Coach already has training at a different location at the same time on " + newTrainingDay.getWeekday());
                   }

               }
           }

       }

        Training training = trainingMapper.toTraining(trainingInfo);
        Training saveTraining = trainingRepository.save(training);
        trainingLocationRepository.updateTrainingLocationTable(saveTraining.getId(),location.getId(),Status.ACTIVE.getCode());
        List<Weekday> all = weekdayRepository.findAll();




    }



}


