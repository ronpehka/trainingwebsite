package com.bcs.trainingwebsite.persistance.training;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("select t from Training t where t.status = :status")
    List<Training> findTrainingsBy(String status);

    @Query("select t from Training t where t.coachUser = :user and t.status = :status")
    List<Training> findTrainingsBy(User user, String status);


}