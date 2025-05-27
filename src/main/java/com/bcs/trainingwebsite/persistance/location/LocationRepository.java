package com.bcs.trainingwebsite.persistance.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {


    @Query("select l from Location l where l.name = :name and l.address = :address")
    Optional<Location> findLocationBy(@Param("name") String name, @Param("address") String address);

    @Query("select (count(l) > 0) from Location l where l.name = :name and l.address = :address")
    boolean locationExists(String name, String address);

}