package com.tripmate.api.controller;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.response.LocationBasedSpotListResponse;
import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import com.tripmate.api.service.LocationBasedSpotSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotSearchService locationBasedSpotSearchService;

    @GetMapping()
    public ResponseEntity<LocationBasedSpotListResponse> getSpots(
            @ModelAttribute LocationBasedSpotSearchRequest request
    ) {
        List<LocationBasedSpotRecord> spots = locationBasedSpotSearchService.searchLocationBasedSpots(request);
        return ResponseEntity.ok(new LocationBasedSpotListResponse(spots));
    }
}
