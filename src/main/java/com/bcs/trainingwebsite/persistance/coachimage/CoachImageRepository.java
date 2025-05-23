package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoachImageRepository extends JpaRepository<CoachImage, Integer> {

    Optional<CoachImage> findByUser_id_Id(Integer id);
}

