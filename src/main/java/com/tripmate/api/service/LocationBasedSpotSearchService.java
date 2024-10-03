package com.tripmate.api.service;

import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.integration.tourapi.domain.SpotContentType;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import com.tripmate.integration.tourapi.service.TourApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationBasedSpotSearchService {

    private final TourApiService tourApiService;

    public List<LocationBasedSpotInfo> searchLocationBasedSpots(LocationBasedSpotSearchRequest request) {
        SpotType spotType = request.spotType();
        SpotContentType tourApiContentType = (spotType != null) ? spotType.getTourApiContentType() : null;

        LocationBasedSpotApiResponse response = tourApiService.findSpotsByLocation(
                request.latitude(),
                request.longitude(),
                request.range(),
                tourApiContentType
        );

        return response.spotItems().stream()
                .parallel()
                .map(item -> {
                    SpotCommonInfo commonInfo = tourApiService.getSpotDetailInfo(item.contentId());
                    return LocationBasedSpotInfo.toLocationBasedSpotInfo(item, commonInfo);
                })
                .filter(item -> spotType == null || item.spotType().equals(spotType))
                .toList();
    }
}
