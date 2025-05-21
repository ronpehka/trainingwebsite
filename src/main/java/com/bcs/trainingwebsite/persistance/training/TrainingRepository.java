package com.bcs.trainingwebsite.persistance.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("select t from Training t where t.status = :status")
    List<Training> findTrainingsBy(String status);

    @Query("select t from Training t where t.coachUser.id = :coachId and t.name = :trainingName")
    Optional<Training> findTrainingBy(Integer coachId, String trainingName);


}