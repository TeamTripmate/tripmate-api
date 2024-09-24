package com.tripmate.integration.tourapi.service;

import com.tripmate.integration.tourapi.TourApiRestClient;
import com.tripmate.integration.tourapi.dto.request.TourApiRequestPath;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
public class TourApiService {

    private final TourApiRestClient tourApiRestClient;

    public LocationBasedSpotApiResponse findSpotsByLocation(String latitude, String longitude, String range) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mapX", longitude);
        queryParams.add("mapY", latitude);
        queryParams.add("radius", range);
        queryParams.add("numOfRows", "20");

        ResponseEntity<LocationBasedSpotApiResponse> response = tourApiRestClient.get(
                TourApiRequestPath.LOCATION_BASED_SPOT.getPath(),
                LocationBasedSpotApiResponse.class,
                queryParams
        );

        return response.getBody();
    }
}
