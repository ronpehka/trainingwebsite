package com.bcs.trainingwebsite.persistance.traininglocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainingLocationRepository extends JpaRepository<TrainingLocation, Integer> {


    @Query("select t.location.id from TrainingLocation t where t.training.id = :trainingId")
    Optional<Integer> findLocationByTrainingId( Integer trainingId);
}