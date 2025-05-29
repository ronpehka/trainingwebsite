package com.bcs.trainingwebsite.persistance.trainingdate;

import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingDateRepository extends JpaRepository<TrainingDate, Integer> {
    @Transactional
    @Modifying
    @Query("update TrainingDate t set t.training.id = :trainingId, t.date = :date")
    void updateTrainingDate(Integer trainingId, LocalDate date);

    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date")
    Optional<TrainingDate> findTrainingDateBy(User user, LocalDate date);

    @Transactional
    @Modifying
    @Query("delete from TrainingDate t where t.training.id = :trainingId")
    void deleteByTrainingId(Integer trainingId);

    @Query("select t from TrainingDate t where t.date = :date")
    List<TrainingDate> findAll();

}