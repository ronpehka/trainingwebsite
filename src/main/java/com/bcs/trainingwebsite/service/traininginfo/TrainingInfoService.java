package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.infrastructure.exception.ForbiddenException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.location.Location;
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
    private final TrainingLocationRepository trainingLocationRepository;
    private final TrainingWeekdayMapper trainingWeekdayMapper;
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


        User userCoach = userRepository.findById(trainingDto.getCoachUserId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("coachUserId", trainingDto.getCoachUserId()));

        Sport sport = sportRepository.findById(trainingDto.getSportId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("sportId", trainingDto.getSportId()));

        Training training = trainingMapper.toTraining(trainingDto);
        training.setCoachUser(userCoach);
        training.setSport(sport);
        trainingRepository.save(training);

        // Determine valid training dates
        List<Integer> availableWeekdays = getAvailableWeekdays(trainingDto.getTrainingDays());
        List<TrainingDate> trainingDates = generateTrainingDates(trainingDto.getStartDate(), trainingDto.getEndDate(), availableWeekdays, training);

        // Check for overlapping training sessions
        validateTrainingTimeConflicts(trainingDates, userCoach, trainingDto);

        // Save valid training dates
        trainingDateRepository.saveAll(trainingDates);
    }

    private List<Integer> getAvailableWeekdays(List<TrainingWeekdayInfo> trainingWeekdayInfos) {
        List<Integer> availableDays = new ArrayList<>();
        for (TrainingWeekdayInfo info : trainingWeekdayInfos) {
            if (info.isAvailable()) {
                availableDays.add(info.getWeekdayNumber());
            }
        }
        return availableDays;
    }

    private List<TrainingDate> generateTrainingDates(LocalDate start, LocalDate end, List<Integer> availableWeekdays, Training training) {
        List<TrainingDate> trainingDates = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int dayOfWeek = date.getDayOfWeek().getValue(); // Monday = 1
            if (availableWeekdays.contains(dayOfWeek)) {
                TrainingDate trainingDate = new TrainingDate();
                trainingDate.setTraining(training);
                trainingDate.setDate(date);
                trainingDates.add(trainingDate);
            }
        }
        return trainingDates;
    }

    private void validateTrainingTimeConflicts(List<TrainingDate> newTrainingDates, User userCoach, TrainingDto trainingDto) {
        LocalTime newStart = TimeConverter.stringToLocalTime(trainingDto.getStartTime());
        LocalTime newEnd = TimeConverter.stringToLocalTime(trainingDto.getEndTime());

        for (TrainingDate newDate : newTrainingDates) {
            Optional<TrainingDate> existing = trainingDateRepository.findTrainingDateBy(userCoach, newDate.getDate());
            if (existing.isPresent()) {
                Training existingTraining = existing.get().getTraining();
                LocalTime existingStart = existingTraining.getStartTime();
                LocalTime existingEnd = existingTraining.getEndTime();

                boolean isOverlapping = newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd);
                if (isOverlapping) {
                    throw new ForbiddenException("Treener ei saa anda kahte trenni korraga", 999);
                }
            }
        }
    }


}


