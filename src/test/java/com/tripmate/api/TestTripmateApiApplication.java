package com.tripmate.api;

import org.springframework.boot.SpringApplication;

public class TestTripmateApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(TripmateApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
