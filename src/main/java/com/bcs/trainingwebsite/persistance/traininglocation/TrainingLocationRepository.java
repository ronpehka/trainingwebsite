package com.bcs.trainingwebsite.persistance.traininglocation;

import com.bcs.trainingwebsite.persistance.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainingLocationRepository extends JpaRepository<TrainingLocation, Integer> {


    @Query("select t.location from TrainingLocation t where t.training.id = :trainingId")
    Optional<Location> findLocationByTrainingId(Integer trainingId);
}