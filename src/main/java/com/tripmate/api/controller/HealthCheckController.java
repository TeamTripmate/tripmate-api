package com.tripmate.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/check")
    public String healthCheck() {

        return "healthCheck";
    }

}
