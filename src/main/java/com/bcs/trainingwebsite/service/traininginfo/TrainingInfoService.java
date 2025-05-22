package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.*;
import com.bcs.trainingwebsite.persistance.district.District;
import com.bcs.trainingwebsite.persistance.district.DistrictRepository;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationMapper;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.sport.Sport;
import com.bcs.trainingwebsite.persistance.sport.SportRepository;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingMapper;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDateRepository;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocationRepository;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayMapper;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayRepository;
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
    private final LocationMapper locationMapper;
    private final TrainingDateRepository trainingDateRepository;
    private final SportRepository sportRepository;

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
    public void addNewTraining(NewTrainingInfo newTrainingInfo) {
        Location location = getLocation(newTrainingInfo);
        LocationInfo locationInfo = locationMapper.toLocationInfo(location);
        LocalTime newTrainingStartTime = TimeConverter.stringToLocalTime(newTrainingInfo.getStartTime());
        LocalTime newTrainingEndTime = TimeConverter.stringToLocalTime(newTrainingInfo.getEndTime());
        List<Training> existingTrainings = trainingRepository.existingTrainings(newTrainingInfo.getCoachUserId(), Status.ACTIVE.getCode());
        List<TrainingInfo> existingTrainingInfos = trainingMapper.toTrainingInfos(existingTrainings);

        for (TrainingInfo existingTrainingInfo : existingTrainingInfos) {
            addTrainingDays(existingTrainingInfo);
            for (TrainingDay existingTrainingDay : existingTrainingInfo.getTrainingDays()) {
                if(newTrainingInfo.getTrainingDays().contains(existingTrainingDay.getWeekday())){
                    boolean sameTime = newTrainingStartTime.equals(TimeConverter.stringToLocalTime(existingTrainingInfo.getStartTime())) && newTrainingEndTime.equals(TimeConverter.stringToLocalTime(existingTrainingInfo.getEndTime()));
                    boolean differentPlace = !existingTrainingInfo.getAddress().equals(locationInfo.getAddress()) && !existingTrainingInfo.getLocationName().equals(locationInfo.getLocationName());
                    if(sameTime && differentPlace){
                        throw new IllegalArgumentException("Treeneril on juba treening teises kohas samal ajal" + existingTrainingDay.getWeekday());
                    }
                }

            }
        }
        Sport sport = getSport(newTrainingInfo);
        Training training = saveSport(newTrainingInfo, sport);
        updateDataBaseTables(training, location);
        updateTrainingWeekDays(newTrainingInfo, training);
    }

    private void updateTrainingWeekDays(NewTrainingInfo newTrainingInfo, Training training) {
        for (TrainingDayInfo trainingDay : newTrainingInfo.getTrainingDays()) {
            trainingWeekdayRepository.updateTrainingWeekDays(training, trainingDay.getWeekDayId());
        }
    }

    private void updateDataBaseTables(Training training, Location location) {
        trainingLocationRepository.updateTrainingLocation(training, location,Status.ACTIVE.getCode());
        trainingDateRepository.updateTrainingDate(training.getId(), training.getStartDate());
        trainingDateRepository.updateTrainingDate(training.getId(), training.getEndDate());
    }

    private Training saveSport(NewTrainingInfo newTrainingInfo, Sport sport) {
        Training training = trainingMapper.toTraining(newTrainingInfo);
        training.setSport(sport);
        trainingRepository.save(training);
        return training;
    }

    private Sport getSport(NewTrainingInfo newTrainingInfo) {
        Sport sport = sportRepository.findSportBy(newTrainingInfo.getSportType()).orElseGet(() -> {
            Sport newSport = new Sport();
            newSport.setName(newTrainingInfo.getSportType());
            return sportRepository.save(newSport);
        });
        return sport;
    }

    private Location getLocation(NewTrainingInfo newTrainingInfo) {
        return locationRepository.findLocationBy(newTrainingInfo.getLocationName(), newTrainingInfo.getAddress())
                .orElseGet(() -> {
                    Location newLocation = new Location();
                    newLocation.setAddress(newTrainingInfo.getAddress());
                    newLocation.setName(newTrainingInfo.getTrainingName());
                    District district = handleDistrict(newTrainingInfo);
                    newLocation.setDistrict(district);
                    return locationRepository.save(newLocation);
                });
    }

    private District handleDistrict(NewTrainingInfo newTrainingInfo) {
        District district = districtRepository.findDistrictBy(newTrainingInfo.getDistrictName())
                .orElseGet(()-> {
                    District newDistrict = new District();
                    newDistrict.setName(newTrainingInfo.getDistrictName());
                    return districtRepository.save(newDistrict);
                });
        return district;
    }
}


