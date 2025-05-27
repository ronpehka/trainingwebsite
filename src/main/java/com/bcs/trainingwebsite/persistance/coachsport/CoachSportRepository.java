package com.bcs.trainingwebsite.persistance.coachsport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoachSportRepository extends JpaRepository<CoachSport, Integer> {

    @Query("select c from CoachSport c where c.coachUser.id = :coachId order by c.sport.name")
    List<CoachSport> findCoachSportsBy(Integer coachId);
}

