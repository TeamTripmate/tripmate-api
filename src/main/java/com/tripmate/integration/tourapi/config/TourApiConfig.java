package com.tripmate.integration.tourapi.config;

import com.tripmate.integration.tourapi.TourApiRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({TourApiProperties.class})
@ComponentScan("com.tripmate.integration.tourapi")
@RequiredArgsConstructor
public class TourApiConfig {

    private final TourApiProperties properties;

    @Bean
    public TourApiRestClient tourApiRestClient() {
        return new TourApiRestClient(
                properties.serviceKey(),
                properties.mobileApp(),
                properties.mobileOS()
        );
    }
}
