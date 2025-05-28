package com.bcs.trainingwebsite.persistance.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("select p from Profile p where p.user.id = :userId")
    Profile findProfileBy(Integer userId);

    @Query("""
            select p from Profile p
            where p.user.role.name = :roleName and p.user.status = :userStatus
            order by p.firstName, p.lastName""")
    List<Profile> findProfilesBy(String roleName, String userStatus);

}