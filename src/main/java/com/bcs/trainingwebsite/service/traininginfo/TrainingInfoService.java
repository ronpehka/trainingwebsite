package com.bcs.trainingwebsite.service.traininginfo;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDay;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDto;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingInfo;
import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingWeekdayInfo;
import com.bcs.trainingwebsite.infrastructure.error.Error;
import com.bcs.trainingwebsite.infrastructure.exception.DataNotFoundException;
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
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
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
import java.util.stream.Collectors;

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
    private final WeekdayRepository weekdayRepository;

    public List<TrainingInfo> getAllTrainingInfo() {
        List<Training> trainings = trainingRepository.findTrainingsBy(Status.ACTIVE.getCode());
        List<TrainingInfo> trainingInfos = trainingMapper.toTrainingInfos(trainings);
        addRemainingInformationToTrainingInfos(trainingInfos);
        return trainingInfos;
    }

    @Transactional
    public Integer addNewTraining(TrainingDto trainingDto) {
        User userCoach = getUserCoach(trainingDto);

        Sport sport = getSport(trainingDto);

        Training training = trainingMapper.toTraining(trainingDto);
        training.setCoachUser(userCoach);
        training.setSport(sport);
        trainingRepository.save(training);

        // Determine valid training dates
        List<Integer> availableWeekdays = getAvailableWeekdays(trainingDto.getTrainingDays());
        List<TrainingDate> trainingDates = generateTrainingDates(trainingDto.getStartDate(), trainingDto.getEndDate(), availableWeekdays, training);

        // Check for overlapping training sessions
        validateTrainingTimeConflicts(trainingDates, userCoach, trainingDto);
        trainingDateRepository.saveAll(trainingDates);

        List<TrainingWeekday> trainingWeekdays = getTrainingWeekdays(trainingDto, training);
        trainingWeekdayRepository.saveAll(trainingWeekdays);
        return training.getId();
    }

    private List<TrainingWeekday> getTrainingWeekdays(TrainingDto trainingDto, Training training) {
        return trainingDto.getTrainingDays().stream()
                .filter(TrainingWeekdayInfo::isAvailable)
                .map(weekDayInfo -> {
                    Weekday weekday = weekdayRepository.findById(weekDayInfo.getWeekdayId())
                            .orElseThrow(() -> new ForeignKeyNotFoundException("weekdayId", weekDayInfo.getWeekdayId()));
                    TrainingWeekday trainingWeekday = new TrainingWeekday();
                    trainingWeekday.setTraining(training);
                    trainingWeekday.setWeekday(weekday);
                    return trainingWeekday;
                })
                .collect(Collectors.toList());
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


    private Sport getSport(TrainingDto trainingDto) {
        return sportRepository.findById(trainingDto.getSportId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("sportId", trainingDto.getSportId()));
    }

    private User getUserCoach(TrainingDto trainingDto) {
        return userRepository.findById(trainingDto.getCoachUserId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("coachUserId", trainingDto.getCoachUserId()));
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
                    throw new ForbiddenException(Error.INCORRECT_TRAINING_TIME.getMessage(), Error.INCORRECT_TRAINING_TIME.getErrorCode());
                }
            }
        }
    }

    @Transactional
    public void updateTrainingInfo(Integer trainingId, TrainingDto trainingDto) {
        User userCoach = getUserCoach(trainingDto);
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new DataNotFoundException(Error.INVALID_REQUEST.getMessage(), Error.INVALID_REQUEST.getErrorCode()));
        trainingMapper.partialUpdate(training, trainingDto);
        handleSportUpdate(trainingDto, training);
        trainingRepository.save(training);
        // Determine valid training dates
        List<Integer> availableWeekdays = getAvailableWeekdays(trainingDto.getTrainingDays());
        List<TrainingDate> trainingDates = generateTrainingDates(trainingDto.getStartDate(), trainingDto.getEndDate(), availableWeekdays, training);

        // Check for overlapping training sessions
        validateTrainingTimeConflicts(trainingDates, userCoach, trainingDto);
        // Save valid training dates
        trainingDateRepository.deleteByTrainingId(training.getId());
        trainingDateRepository.saveAll(trainingDates);
        List<TrainingWeekday> trainingWeekdays = getTrainingWeekdays(trainingDto, training);
        trainingWeekdayRepository.deleteBy(trainingId);
        trainingWeekdayRepository.saveAll(trainingWeekdays);


    }

    private void handleSportUpdate(TrainingDto trainingDto, Training training) {
        Integer trainingDtoSportId = trainingDto.getSportId();
        Integer trainingSportId = training.getSport().getId();
        if (!trainingDtoSportId.equals(trainingSportId)) {
            Sport sport = getSport(trainingDto);
            training.setSport(sport);
        }
    }

    public List<TrainingInfo> getTrainingsBySportName(String sportName) {
        List<Training> trainings = trainingRepository.findTrainingsByName(sportName);
        return trainingMapper.toTrainingInfos(trainings);
    }

    public List<TrainingInfo> getTrainingsBySportId(Integer sportId) {
        List<TrainingInfo> trainingInfos = trainingRepository.findTrainingsBySportId(sportId)
                .stream()
                .map(trainingMapper::toTrainingInfo)
                .collect(Collectors.toList());

        addRemainingInformationToTrainingInfos(trainingInfos);  // << ADD THIS LINE

        return trainingInfos;
    }

    public TrainingDto getTrainingInfo(Integer trainingId) {
        Training training = trainingRepository.getReferenceById(trainingId);
        TrainingDto trainingDto = trainingMapper.toTrainingDto(training);

        List<TrainingWeekday> trainingWeekdays = trainingWeekdayRepository.findTrainingWeekdaysBy(trainingId);
        List<TrainingWeekdayInfo> trainingWeekdayInfos = trainingWeekdayMapper.toTrainingWeekdayInfos(trainingWeekdays);
        trainingDto.setTrainingDays(trainingWeekdayInfos);


        return trainingDto;
    }
}


