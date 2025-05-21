package com.bcs.trainingwebsite.persistance.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("select p from Profile p where p.user.id = :userId")
    Profile findProfileBy(Integer userId);
}