package com.bcs.trainingwebsite.persistance.trainingweekday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingWeekdayRepository extends JpaRepository<TrainingWeekday, Integer> {


    @Query("select t from TrainingWeekday t where t.training.id = :trainingId")
    List<TrainingWeekday> findTrainingWeekdayBy(Integer trainingId);
}