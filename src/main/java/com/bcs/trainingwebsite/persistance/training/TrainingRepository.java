package com.bcs.trainingwebsite.persistance.training;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("select t from Training t where t.status = :status")
    List<Training> findTrainingsBy(String status);

    @Query("select t from Training t where t.coachUser = :user and t.status = :status")
    List<Training> findTrainingsBy(User user, String status);

    @Query("select t from Training t where t.id = :trainingId and t.status = :status")
    Optional<Training> findTrainingBy(Integer trainingId, String status);

    @Query("select t from Training t where t.sport.id = :sportId")
    List<Training> findTrainingsBySportId(Integer sportId);

    @Query("select t from Training t where t.sport.name = :sportName")
    List<Training> findTrainingsByName(@Param("sportName") String sportName);

    @Query("select t from Training t where t.coachUser.id = :coachId and t.id = :trainingId")
    Training findTrainingBy(Integer coachId,  Integer trainingId);


}