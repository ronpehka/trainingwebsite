package com.bcs.trainingwebsite.persistance.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("select t from Training t where t.status = :status")
    List<Training> findTrainingsBy(String status);

    @Query("select t from Training t where t.id = :trainingId and t.status = :status")
    Optional<Training> findTrainingBy(Integer trainingId, String status);

    @Query("select t from Training t where t.sport.id = :sportId and t.status = :status")
    List<Training> findTrainingsBySportId(Integer sportId, String status);

    @Query("select t from Training t where t.sport.name = :sportName")
    List<Training> findTrainingsByName(@Param("sportName") String sportName);

}