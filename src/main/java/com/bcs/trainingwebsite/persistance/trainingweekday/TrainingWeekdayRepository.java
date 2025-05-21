package com.bcs.trainingwebsite.persistance.trainingweekday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface TrainingWeekdayRepository extends JpaRepository<TrainingWeekday, Integer> {


    @Query("select t from TrainingWeekday t where t.training.id = :trainingId")
    List<TrainingWeekday> findTrainingWeekdaysBy(Integer trainingId);


}