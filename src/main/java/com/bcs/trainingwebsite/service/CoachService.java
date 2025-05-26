package com.bcs.trainingwebsite.service;


import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {


    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public List<CoachInfo> findAllCoachesInfo() {
        List<Profile> profiles = profileRepository.findProfilesBy("coach", Status.ACTIVE.getCode());
        List<CoachInfo> coachInfos = profileMapper.toCoachInfos(profiles);
        return coachInfos;
    }
}
