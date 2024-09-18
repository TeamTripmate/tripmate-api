package com.tripmate.api;

import com.tripmate.api.config.AppConfig;
import com.tripmate.integration.tourapi.config.TourApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AppConfig.class, TourApiConfig.class })
public class TripmateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripmateApiApplication.class, args);
    }

}
