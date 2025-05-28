package com.bcs.trainingwebsite.persistance.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Integer> {
    Optional<Register> findByUserIdAndTrainingId(Integer userId, Integer trainingId);

    @Query("select count (r) from Register r where r.training.id = :trainingId and r.status = 'A'")
    int countActiveRegistrationsByTrainingId(@Param("trainingId") Integer trainingId);

}
