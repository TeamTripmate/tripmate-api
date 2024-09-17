package com.tripmate.api.controller;

import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import com.tripmate.api.service.LocationBasedSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotService locationBasedSpotService;

    @GetMapping()
    public ResponseEntity<List<LocationBasedSpotRecord>> getSpots(
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String range
    ) {
        List<LocationBasedSpotRecord> spots = locationBasedSpotService.findSpotsByLocation(latitude, longitude, range);
        return ResponseEntity.ok(spots);
    }
}
