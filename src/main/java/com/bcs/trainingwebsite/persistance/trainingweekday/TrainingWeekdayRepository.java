package com.bcs.trainingwebsite.persistance.trainingweekday;

import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.weekday.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TrainingWeekdayRepository extends JpaRepository<TrainingWeekday, Integer> {


    @Query("select t from TrainingWeekday t where t.training.id = :trainingId")
    List<TrainingWeekday> findTrainingWeekdaysBy(Integer trainingId);

    @Transactional
    @Modifying
    @Query("update TrainingWeekday t set t.training = :training, t.weekday.id = :weekdayId")
    void updateTrainingWeekDays(Training training, Integer weekdayId);


}