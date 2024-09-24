package com.tripmate.api.controller;

import com.tripmate.api.dto.request.CompanionApplyRequest;
import com.tripmate.api.dto.request.CollectCompanionRequest;
import com.tripmate.api.dto.request.CompanionReviewRequest;
import com.tripmate.api.dto.response.CollectCompanionResponse;
import com.tripmate.api.dto.response.CompanionInfoResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.service.CompanionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companions")
public class CompanionController {

    private final CompanionService companionService;

    @Operation(
        summary = "동행 모집 생성 API",
        description = ""
    )
    @PostMapping("")
    public ResponseEntity<TripmateApiResponse<CollectCompanionResponse>> collectCompanion(@Valid @RequestBody CollectCompanionRequest collectCompanionRequest) {

        CollectCompanionResponse collectCompanionResponse = companionService.saveCompanionInfo(collectCompanionRequest);
        return ResponseEntity.ok(TripmateApiResponse.success(collectCompanionResponse));
    }

    @Operation(
        summary = "(유저가 작성한) 동행 상세 정보 조회 API",
        description = ""
    )
    @GetMapping("/user/{companionId}")
    public ResponseEntity<TripmateApiResponse<CompanionInfoResponse>> getCompanionInfo(@PathVariable("companionId") Long companionId) {

        CompanionInfoResponse companionInfo = companionService.getCompanionInfo(companionId);

        return ResponseEntity.ok(
            TripmateApiResponse.success(companionInfo));
    }

    @Operation(
        summary = "동행 리뷰 생성 API",
        description = ""
    )
    @PostMapping("/review")
    public ResponseEntity<Void> createCompanionReview(@AuthenticationPrincipal Long userId, @Valid @RequestBody CompanionReviewRequest companionReviewRequest) {
        companionService.saveCompanionReview(userId, companionReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
     }

    @Operation(
        summary = "동행 신청 API",
        description = ""
    )
    @PostMapping("/apply")
    public ResponseEntity<Void> createCompanionApply(@Valid @RequestBody CompanionApplyRequest companionApplyRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
     }

}
