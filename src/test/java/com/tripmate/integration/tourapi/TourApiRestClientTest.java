package com.tripmate.integration.tourapi;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

class TourApiRestClientTest {

    @Mock
    private RestClient restClient;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private TourApiRestClient tourApiRestClient;

    @Test
    public void testGet() {
        String path = "/B551011/KorService1";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    }
}