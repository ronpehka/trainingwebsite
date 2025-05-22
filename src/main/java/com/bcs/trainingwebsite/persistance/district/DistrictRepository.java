package com.bcs.trainingwebsite.persistance.district;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query("select d from District d where d.name = :districtName")
    Optional<District> findDistrictBy(String districtName);
}