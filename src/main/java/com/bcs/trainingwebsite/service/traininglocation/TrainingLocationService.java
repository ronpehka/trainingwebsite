package com.bcs.trainingwebsite.service.traininglocation;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.location.LocationRepository;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocation;
import com.bcs.trainingwebsite.persistance.traininglocation.TrainingLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingLocationService {


    private final TrainingRepository trainingRepository;
    private final LocationRepository locationRepository;
    private final TrainingLocationRepository trainingLocationRepository;


    public void addTrainingLocation(Integer trainingId, Integer locationId) {
        Training training = trainingRepository.findTrainingBy(trainingId, Status.ACTIVE.getCode())
                .orElseThrow(() -> new ForeignKeyNotFoundException("trainingId", trainingId));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("trainingId", trainingId));

        TrainingLocation trainingLocation = new TrainingLocation();
        trainingLocation.setTraining(training);
        trainingLocation.setLocation(location);
        trainingLocation.setStatus(Status.ACTIVE.getCode());

        trainingLocationRepository.save(trainingLocation);
    }

}
