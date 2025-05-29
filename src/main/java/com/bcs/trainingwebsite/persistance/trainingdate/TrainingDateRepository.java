package com.bcs.trainingwebsite.persistance.trainingdate;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface TrainingDateRepository extends JpaRepository<TrainingDate, Integer> {

    @Query("select t from TrainingDate t where t.training.coachUser = :user and t.date = :date")
    Optional<TrainingDate> findTrainingDateBy(User user, LocalDate date);

    @Transactional
    @Modifying
    @Query("delete from TrainingDate t where t.training.id = :trainingId")
    void deleteBy(Integer trainingId);


}