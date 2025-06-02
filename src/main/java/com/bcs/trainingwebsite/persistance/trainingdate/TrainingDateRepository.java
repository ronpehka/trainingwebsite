package com.bcs.trainingwebsite.persistance.trainingdate;

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

//    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date")
//    Optional<TrainingDate> findTrainingDateBy(User user, LocalDate date);
//    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date and t.training.id <> :trainingId")
//    Optional<TrainingDate> findTrainingDate(User user, LocalDate date, Integer trainingId);


    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date")
    List<TrainingDate> findTrainingDatesByCoachAndDate(User user, LocalDate date);


    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date and t.training.id <> :trainingId")
    List<TrainingDate> findTrainingDatesByCoachAndDateExcludingTraining(User user, LocalDate date, Integer trainingId);
    @Transactional
    @Modifying
    @Query("delete from TrainingDate t where t.training.id = :trainingId")
    void deleteBy(Integer trainingId);

    List<TrainingDate> findAll();

}