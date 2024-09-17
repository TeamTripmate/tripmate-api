package com.tripmate.api.service;

import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import com.tripmate.integration.tourapi.service.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationBasedSpotService {

    private final TourApiService tourApiService;

    public List<LocationBasedSpotRecord> findSpotsByLocation(String latitude, String longitude, String range) {
        LocationBasedSpotApiResponse response = tourApiService.findSpotsByLocation(latitude, longitude, range);

        return response.spotItems().stream()
                .map(item -> LocationBasedSpotRecord.builder()
                        .id(item.contentId())
                        .title(item.title())
                        .latitude(item.mapX())
                        .longitude(item.mapY())
                        .thumbnailUrl(item.firstImage2())
                        .distance(item.dist())
                        .build()
                ).toList();
    }
}
