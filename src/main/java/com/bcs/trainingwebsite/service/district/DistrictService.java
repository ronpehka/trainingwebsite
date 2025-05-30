package com.bcs.trainingwebsite.service.district;


import com.bcs.trainingwebsite.controller.location.dto.DistrictDto;
import com.bcs.trainingwebsite.controller.location.dto.DistrictInfo;
import com.bcs.trainingwebsite.persistance.district.District;
import com.bcs.trainingwebsite.persistance.district.DistrictMapper;
import com.bcs.trainingwebsite.persistance.district.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictMapper districtMapper;
    private final DistrictRepository districtRepository;

    public void addNewDistrict(DistrictDto districtDto) {
        District district = districtMapper.toDistrict(districtDto);
        districtRepository.save(district);
    }

    public List<DistrictInfo> getAllDistricts() {
        List<District> districts = districtRepository.findAll();
        return districtMapper.toDistrictInfos(districts);
    }
}
