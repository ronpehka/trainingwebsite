package com.bcs.trainingwebsite.service.sport;

import com.bcs.trainingwebsite.controller.sport.dto.SportInfo;
import com.bcs.trainingwebsite.persistence.sport.Sport;
import com.bcs.trainingwebsite.persistence.sport.SportMapper;
import com.bcs.trainingwebsite.persistence.sport.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;
    private final SportMapper sportMapper;


    public List<SportInfo> getAllSports() {
        List<Sport> sports = sportRepository.findAll();
        return sportMapper.toSportInfos(sports);
    }
}
