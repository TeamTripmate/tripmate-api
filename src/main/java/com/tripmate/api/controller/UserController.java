package com.tripmate.api.controller;

import com.tripmate.api.dto.request.TripmatePersonalizedTestRequest;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.dto.response.TripmatePersonalizedTestResponse;
import com.tripmate.api.service.TripmatePersonalizedTestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final TripmatePersonalizedTestService tripmatePersonalizedTestService;

    public UserController(TripmatePersonalizedTestService tripmatePersonalizedTestService) {
        this.tripmatePersonalizedTestService = tripmatePersonalizedTestService;
    }

    @Operation(
            summary = "Tripmate 개인화 설문(여행 스타일 및 호구조사) API"
    )
    @PostMapping("{userId}/personalized-tests")
    public ResponseEntity<TripmateApiResponse<TripmatePersonalizedTestResponse>> submitPersonalizedTest(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody TripmatePersonalizedTestRequest tripmatePersonalizedTestRequest
    ) {
        TripmatePersonalizedTestResponse response = tripmatePersonalizedTestService
                .submitTripmatePersonalizedTest(userId, tripmatePersonalizedTestRequest);

        return ResponseEntity.ok(TripmateApiResponse.success(response));
    }
}
