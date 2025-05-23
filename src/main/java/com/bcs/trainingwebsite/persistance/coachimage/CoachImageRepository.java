package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoachImageRepository extends JpaRepository<CoachImage, Integer> {
    @Query("select c from CoachImage c where c.user_id = ?1")
    CoachImage findByUser_id(User user_id);
}

