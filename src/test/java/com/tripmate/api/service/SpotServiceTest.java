package com.tripmate.api.service;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.spot.LocationBasedSpotRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SpotServiceTest {

    @Mock
    private RestClient restClient;

    @Autowired
    private LocationBasedSpotSearchService locationBasedSpotSearchService;

    @Test
    public void testFindSpotsByLocation() {
        // given
        LocationBasedSpotSearchRequest request = new LocationBasedSpotSearchRequest(
                "127.1225635",
                "37.4855846",
                "10000.0",
                null
        );

        List<LocationBasedSpotRecord> spots = locationBasedSpotSearchService.searchLocationBasedSpots(request);


    }

}