package com.bcs.trainingwebsite.controller.location;

import com.bcs.trainingwebsite.controller.location.dto.DistrictDto;
import com.bcs.trainingwebsite.service.district.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;

    @PostMapping("/new-district")
    public void addNewDistrict(@RequestBody DistrictDto districtDto) {
        districtService.addNewDistrict(districtDto);
    }
}
