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

    @Query("""
            select t from TrainingWeekday t
            where t.training.id = :id and t.training.coachUser.id = :coachId and t.training.startTime = :startTime and t.training.endTime = :endTime and t.weekday.shortField = :weekday""")
    List<TrainingWeekday> findCoachesTraining(Integer trainingId, Integer coachId,  LocalTime startTime,  LocalTime endTime, String weekday);

    @Query("select t from TrainingWeekday t where t.training.id = :trainingId and t.weekday.shortField = :weekDayName")
    Optional<TrainingWeekday> findTrainingWeekdayBy( Integer trainingId,  String weekDayName);

}