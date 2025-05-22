package com.bcs.trainingwebsite.persistance.trainingdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface TrainingDateRepository extends JpaRepository<TrainingDate, Integer> {
    @Transactional
    @Modifying
    @Query("update TrainingDate t set t.training.id = :trainingId, t.date = :date")
    void updateTrainingDate(Integer trainingId, LocalDate date);
}