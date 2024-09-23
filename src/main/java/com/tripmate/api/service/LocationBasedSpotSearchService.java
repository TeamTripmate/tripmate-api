package com.tripmate.api.service;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import com.tripmate.integration.tourapi.service.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationBasedSpotSearchService {

    private final TourApiService tourApiService;

    public List<LocationBasedSpotRecord> searchLocationBasedSpots(LocationBasedSpotSearchRequest request) {
        LocationBasedSpotApiResponse response = tourApiService.findSpotsByLocation(
                request.latitude(),
                request.longitude(),
                request.range()
        );

        return response.spotItems().stream()
                .map(item -> LocationBasedSpotRecord.builder()
                        .id(item.contentId())
                        .title(item.title())
                        .description("Tripmate API에서 지원할 예정")
                        .latitude(item.mapY())
                        .longitude(item.mapX())
                        .thumbnailUrl(item.firstImage2())
                        .distance(item.dist())
                        .build()
                ).toList();
    }
}
