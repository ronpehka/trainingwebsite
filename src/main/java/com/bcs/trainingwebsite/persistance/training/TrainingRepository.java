package com.bcs.trainingwebsite.persistance.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("select t from Training t where t.status = :status")
    List<Training> findTrainingsBy(String status);
}