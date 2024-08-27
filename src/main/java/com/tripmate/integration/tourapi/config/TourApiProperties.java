package com.tripmate.integration.tourapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("integration.tourapi")
public record TourApiProperties(String serviceKey, String mobileApp, String mobileOS) {
}
