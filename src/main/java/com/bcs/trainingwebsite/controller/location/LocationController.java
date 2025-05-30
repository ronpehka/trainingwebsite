package com.bcs.trainingwebsite.controller.location;

import com.bcs.trainingwebsite.controller.location.dto.LocationDto;
import com.bcs.trainingwebsite.controller.location.dto.LocationInfo;
import com.bcs.trainingwebsite.infrastructure.error.ApiError;
import com.bcs.trainingwebsite.service.LocationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return locationInfos;
    }

    @PostMapping("/new-location")
    public void addNewLocation(@RequestBody LocationDto locationDto) {
        locationService.addNewLocation(locationDto);
    }

    @DeleteMapping("/locations")
    public void removeLocation(@RequestParam Integer locationId){
        locationService.removeLocation(locationId);
    }

    @PutMapping("/location")
    public void updateLocation(@RequestParam Integer locationId, @RequestBody LocationDto locationDto){
        locationService.updateLocation(locationId,locationDto);
    }
}
