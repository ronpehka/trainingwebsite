package com.bcs.trainingwebsite.service;


import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachInfo;
import com.bcs.trainingwebsite.controller.coachinfo.dto.CoachSportInfo;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImage;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageMapper;
import com.bcs.trainingwebsite.persistance.coachimage.CoachImageRepository;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSport;
import com.bcs.trainingwebsite.persistance.coachsport.CoachSportRepository;
import com.bcs.trainingwebsite.persistance.profile.Profile;
import com.bcs.trainingwebsite.persistance.profile.ProfileMapper;
import com.bcs.trainingwebsite.persistance.profile.ProfileRepository;
import com.bcs.trainingwebsite.persistance.sport.Sport;
import com.bcs.trainingwebsite.persistance.sport.SportMapper;
import com.bcs.trainingwebsite.persistance.sport.SportRepository;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.util.ImageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {


    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final CoachSportRepository coachSportRepository;
    private final TrainingRepository trainingRepository;
    private final CoachImageRepository coachImageRepository;
    private final SportRepository sportRepository;
    private final SportMapper sportMapper;
    private final CoachImageMapper coachImageMapper;

    public List<CoachInfo> findAllCoachesInfo() {
        List<Profile> profiles = profileRepository.findProfilesBy("coach", Status.ACTIVE.getCode());
        List<CoachInfo> coachInfos = profileMapper.toCoachInfos(profiles);
        List<Sport> sports = new ArrayList<>();

        for (CoachInfo coachInfo : coachInfos) {
            Integer coachUserId = coachInfo.getCoachUserId();
            List<CoachSport> coachSports = coachSportRepository.findCoachSportsBy(coachUserId);
            for (CoachSport coachSport : coachSports) {
                Sport sport = sportRepository.findById(coachSport.getSport().getId()).orElseThrow(() -> new ForeignKeyNotFoundException("sportId", coachSport.getId()));
                sports.add(sport);
            }

            List<CoachSportInfo> coachSportInfos = sportMapper.toCoachSportInfos(sports);
            coachInfo.setSports(coachSportInfos);

            Optional<CoachImage> optionalCoachImage = coachImageRepository.findImageBy(coachInfo.getCoachUserId());
            if (optionalCoachImage.isPresent()) {
                byte[] bytes = optionalCoachImage.get().getData();
                String imageData = ImageConverter.bytesToString(bytes);
                coachInfo.setImageData(imageData);
            }
        }
        return coachInfos;
    }


}

