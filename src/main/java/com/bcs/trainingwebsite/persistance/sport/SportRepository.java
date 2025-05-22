package com.bcs.trainingwebsite.persistance.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Integer> {


    @Query("select s from Sport s where s.name = :name")
    Optional<Sport> findSportBy( String name);
}