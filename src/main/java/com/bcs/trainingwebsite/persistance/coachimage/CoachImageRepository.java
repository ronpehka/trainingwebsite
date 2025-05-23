package com.bcs.trainingwebsite.persistance.coachimage;

import com.bcs.trainingwebsite.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CoachImageRepository extends JpaRepository<CoachImage, Integer> {


    @Query("select c from CoachImage c where c.user.id = ?1")
    Optional<CoachImage> findCoachImageByUser(Integer id);

    @Transactional
    @Modifying
    @Query("delete from CoachImage c where c.data = ?1")
    int deleteCoachImageByData(byte[] data);


}