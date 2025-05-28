package com.bcs.trainingwebsite.persistance.register;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Integer> {
    Optional<Register> findByUserIdAndTrainingId(Integer userId, Integer trainingId);

}
