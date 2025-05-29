package com.bcs.trainingwebsite.service.trainingdate;

import com.bcs.trainingwebsite.controller.traininginfo.dto.TrainingDateDto;
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDate;
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDateMapper;
import com.bcs.trainingwebsite.persistance.trainingdate.TrainingDateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingDateService {

    private final TrainingDateRepository trainingDateRepository;
    private final TrainingDateMapper trainingDateMapper;

    public TrainingDateService(TrainingDateRepository trainingDateRepository,
                               TrainingDateMapper trainingDateMapper) {
        this.trainingDateRepository = trainingDateRepository;
        this.trainingDateMapper = trainingDateMapper;
    }

    public List<TrainingDateDto> getAllTrainingDates() {
        List<TrainingDate> trainingDates = trainingDateRepository.findAll();
        return trainingDateMapper.toTrainingDateDtos(trainingDates);
    }
}