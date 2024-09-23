package com.tripmate.api.service;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import com.tripmate.integration.tourapi.service.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationBasedSpotSearchService {

    private final TourApiService tourApiService;

    public List<LocationBasedSpotInfo> searchLocationBasedSpots(LocationBasedSpotSearchRequest request) {
        LocationBasedSpotApiResponse response = tourApiService.findSpotsByLocation(
                request.latitude(),
                request.longitude(),
                request.range()
        );

       return response.spotItems().stream()
                .map(LocationBasedSpotInfo::fromLocationBasedSpotItem)
                .toList();
    }
}
