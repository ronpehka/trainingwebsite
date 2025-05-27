package com.bcs.trainingwebsite.persistance.district;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findById(Integer id);

    @Query("select d from District d where d.name = :districtName")
    Optional<District> findByName(String districtName);

}
