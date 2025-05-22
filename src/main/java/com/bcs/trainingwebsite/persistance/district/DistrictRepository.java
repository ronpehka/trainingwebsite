package com.bcs.trainingwebsite.persistance.district;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findById(Integer id);
}
