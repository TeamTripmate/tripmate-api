package com.tripmate.api.controller;

import com.tripmate.api.dto.response.SpotResponse;
import com.tripmate.api.service.SpotService;
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

    private final SpotService spotService;

    @GetMapping()
    public ResponseEntity<List<SpotResponse>> getSpots(
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String range
    ) {
        List<SpotResponse> spots = spotService.findSpotsByLocation(latitude, longitude, range);
        return ResponseEntity.ok(spots);
    }
}
