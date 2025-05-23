package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
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
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDate;
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDateRepository;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocationRepository;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekday;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayMapper;
import com.bcs.trainingwebsite.persistance.trainingweekday.TrainingWeekdayRepository;
import com.bcs.trainingwebsite.persistance.user.User;
import com.bcs.trainingwebsite.persistance.user.UserRepository;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayMapper;
import com.bcs.trainingwebsite.persistance.weekday.WeekdayRepository;
import com.bcs.trainingwebsite.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private final UserRepository userRepository;

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
    public void addNewTraining(TrainingDto trainingDto) {


        User userCoach = userRepository.findById(trainingDto.getCoachUserId()).orElseThrow(() -> new ForeignKeyNotFoundException("coachUserId", trainingDto.getCoachUserId()));
        Sport sport = sportRepository.findById(trainingDto.getSportId()).orElseThrow(() -> new ForeignKeyNotFoundException("sportId", trainingDto.getSportId()));

        List<Training> existingTrainings = trainingRepository.findTrainingsBy(userCoach, Status.ACTIVE.getCode());
        Training training = trainingMapper.toTraining(trainingDto);
        training.setCoachUser(userCoach);
        training.setSport(sport);
        trainingRepository.save(training);


        LocalDate startDate = trainingDto.getStartDate();
        LocalDate endDate = trainingDto.getEndDate();
        List<TrainingWeekdayInfo> trainingWeekdayInfos = trainingDto.getTrainingDays();
        List<LocalDate> localDates = new ArrayList<>();

        List<Integer> availableWeekdays = new ArrayList<>();
        for (TrainingWeekdayInfo info : trainingWeekdayInfos) {
            if (info.isAvailable()) {
                availableWeekdays.add(info.getWeekdayNumber());
            }
        }
        
        List<TrainingDate> trainingDates = new ArrayList<>();

        List<LocalDate> filteredDates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int currentDayOfWeek = date.getDayOfWeek().getValue(); // Monday = 1, Sunday = 7
            if (availableWeekdays.contains(currentDayOfWeek)) {
                TrainingDate trainingDate = new TrainingDate();
                trainingDate.setTraining(training);
                trainingDate.setDate(date);
                trainingDates.add(trainingDate);
            }
        }


        LocalTime newTrainingStartTime = TimeConverter.stringToLocalTime(trainingDto.getStartTime());
        LocalTime newTrainingEndTime = TimeConverter.stringToLocalTime(trainingDto.getEndTime());

        for (TrainingDate trainingDate : trainingDates) {
            Optional<TrainingDate> optionalTrainingDate = trainingDateRepository.findTrainingDateBy(userCoach, trainingDate.getDate());
            if (optionalTrainingDate.isPresent()) {
                TrainingDate existingTrainingDate = optionalTrainingDate.get();
                LocalTime existingTrainingDateStartTime = existingTrainingDate.getTraining().getStartTime();
                LocalTime existingTrainingDateEndTime = existingTrainingDate.getTraining().getEndTime();

                boolean isOverlapping = newTrainingStartTime.isBefore(existingTrainingDateEndTime) &&
                        existingTrainingDateStartTime.isBefore(newTrainingEndTime);

                if (isOverlapping) {
                    throw new ForbiddenException("ei saaa bla bla bla", 999);
                }

            }
        }

        trainingDateRepository.saveAll(trainingDates);

    }

}


