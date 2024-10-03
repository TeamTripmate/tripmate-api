package com.tripmate.integration.tourapi.service;

import com.tripmate.integration.tourapi.TourApiRestClient;
import com.tripmate.integration.tourapi.domain.SpotContentType;
import com.tripmate.integration.tourapi.dto.request.TourApiRequestPath;
import com.tripmate.integration.tourapi.dto.response.LocationBasedSpotApiResponse;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfoApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@RequiredArgsConstructor
public class TourApiService {

    private final TourApiRestClient tourApiRestClient;

    public LocationBasedSpotApiResponse findSpotsByLocation(String latitude, String longitude, String range) {
        return findSpotsByLocation(latitude, longitude, range, null);
    }

    public LocationBasedSpotApiResponse findSpotsByLocation(
            String latitude, String longitude, String range, SpotContentType spotContentType
    ) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        if (spotContentType != null) {
            queryParams.add("contentTypeId", spotContentType.getContentId());
        }

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

    public SpotCommonInfo getSpotDetailInfo(Long spotId) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("contentId", spotId.toString());
        queryParams.add("overviewYN", "Y");
        queryParams.add("mapinfoYN", "Y");
        queryParams.add("addrinfoYN", "Y");
        queryParams.add("catcodeYN", "Y");
        queryParams.add("defaultYN", "Y");
        queryParams.add("firstImageYN", "Y");

        ResponseEntity<SpotCommonInfoApiResponse> response = tourApiRestClient.get(
                TourApiRequestPath.SPOT_COMMON_INFO.getPath(),
                SpotCommonInfoApiResponse.class,
                queryParams
        );

        return Objects.requireNonNull(response.getBody()).spotItems().getFirst();
    }
}
