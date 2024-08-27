package com.tripmate.integration.tourapi;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

public class TourApiRestClient {

    private final RestClient restClient;

    private final String serviceKey;

    private final String mobileApp;

    private final String mobileOS;

    public TourApiRestClient(String serviceKey, String mobileApp, String mobileOS) {
        String BASE_URL = "https://apis.data.go.kr";
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
        this.serviceKey = serviceKey;
        this.mobileApp = mobileApp;
        this.mobileOS = mobileOS;
    }

    public <T> T get(String path, Class<T> responseType, MultiValueMap<String, String> queryParams) {
        return this.restClient.get()
                .uri(buildUri(path, queryParams))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(responseType);
    }

    private Function<UriBuilder, URI> buildUri(String path, MultiValueMap<String, String> queryParams) {
        return uriBuilder -> {
            UriBuilder builder = uriBuilder
                    .path(path)
                    .queryParam("serviceKey", "{serviceKey}")
                    .queryParam("MobileApp", mobileApp)
                    .queryParam("MobileOS", mobileOS);

            if (queryParams != null && !queryParams.isEmpty()) {
                builder.queryParams(queryParams);
            }

            return builder.build(serviceKey);
        };
    }
}
