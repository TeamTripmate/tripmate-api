package com.tripmate.api.controller;

import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo")
public class DemoController {

    private final DemoService demoService;

    @Operation(
        summary = "데모 엔티티 데이터 생성용 API",
        description = "user1, user2, 여행스타일, 동행모집, 동행객 엔티티 생성 후, id값들 제공"
    )
    @PostMapping("/")
    public ResponseEntity<TripmateApiResponse<Map<String, Long>>> entitySettings() {

        Map<String, Long> stringLongMap = demoService.doEntitySettings();

        return ResponseEntity.ok(TripmateApiResponse.success(stringLongMap));


    }

}
