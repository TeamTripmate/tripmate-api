package com.tripmate.api.service;

import com.tripmate.api.dto.response.SpotResponse;
import com.tripmate.integration.tourapi.TourApiRestClient;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final TourApiRestClient tourApiRestClient;

    public List<SpotResponse> findSpotsByLocation(String latitude, String longitude, String range) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mapX", latitude);
        queryParams.add("mapY", longitude);
        queryParams.add("radius", range);

        ResponseEntity<LocationBasedSpotListResponse> response = tourApiRestClient.get(
                "B551011/KorService1/locationBasedList1",
                LocationBasedSpotListResponse.class,
                queryParams
        );

        return null;
    }
}
