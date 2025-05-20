package com.bcs.trainingwebsite.persistance.weekday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekdayRepository extends JpaRepository<Weekday, Integer> {



}