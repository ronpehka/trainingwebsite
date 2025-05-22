package com.bcs.trainingwebsite.persistance.traininglocation;

import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.persistance.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

public interface TrainingLocationRepository extends JpaRepository<TrainingLocation, Integer> {


    @Query("select t.location from TrainingLocation t where t.training.id = :trainingId")
    Optional<Location> findLocationByTrainingId(Integer trainingId);

    @Query("""
            select (count(t) > 0) from TrainingLocation t
            where t.training.coachUser.id = :coachId and t.training.startTime = :startTime and t.training.endTime = :endTime and t.location.id = :locationId and t.status = :status""")
    boolean trainingExists(Integer coachId, LocalTime startTime,LocalTime endTime, Integer locationId, String status);

    @Transactional
    @Modifying
    @Query("update TrainingLocation t set t.training.id = :training, t.location.id = :locationId, t.status = :status")
    void updateTrainingLocationTable( Integer trainingId, Integer locationId, String status);




}