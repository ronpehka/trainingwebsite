package com.bcs.trainingwebsite.persistance.traininglocation;

import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

public interface TrainingLocationRepository extends JpaRepository<TrainingLocation, Integer> {


    @Query("select t.location from TrainingLocation t where t.training.id = :trainingId")
    Optional<Location> findLocationByTrainingId(Integer trainingId);

    @Transactional
    @Modifying
    @Query("update TrainingLocation t set t.training = :training, t.location = :location, t.status = :status")
    void updateTrainingLocation(@Param("training") Training training,
                                              Location location,String status);


}