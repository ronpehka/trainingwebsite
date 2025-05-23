package com.bcs.trainingwebsite.controller.location;

import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.infrastructure.error.ApiError;
import com.bcs.trainingwebsite.persistance.location.Location;
import com.bcs.trainingwebsite.service.LocationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    @GetMapping("/locations")
    @ApiResponse(responseCode = "404", description = "Ei leitud ühtegi asukohta",
    content = @Content(schema = @Schema(implementation = ApiError.class)))

    public List<LocationInfo> getAllLocations() {
        List<LocationInfo> locationInfos = locationService.getAllLocations();
        return  locationInfos;
    }

}
