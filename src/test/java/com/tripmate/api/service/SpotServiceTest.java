package com.tripmate.api.service;

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
    private LocationBasedSpotService locationBasedSpotService;

    @Test
    public void testFindSpotsByLocation() {
        // given
        String latitude = "127.1225635";
        String longitude = "37.4855846";
        String range = "10000";

        List<LocationBasedSpotRecord> spots = locationBasedSpotService.findSpotsByLocation(latitude, longitude, range);


    }

}