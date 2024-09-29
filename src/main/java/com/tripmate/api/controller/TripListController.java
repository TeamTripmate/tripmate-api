package com.tripmate.api.controller;

import com.tripmate.api.dto.request.ChooseCompanionRequest;
import com.tripmate.api.dto.response.MyApplyCompanionListResponse;
import com.tripmate.api.dto.response.MyCollectCompanionListResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.service.TripListService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip-list")
public class TripListController {

    private final TripListService tripListService;

    @Operation(
        summary = "신청한 동행 목록 API",
        description = ""
    )
    @GetMapping("/apply/companions/{userId}")
    public ResponseEntity<TripmateApiResponse<List<MyApplyCompanionListResponse>>> getAppliedCompanions(
        @PathVariable("userId") Long userId) {

        List<MyApplyCompanionListResponse> myAppliedCompanionList = tripListService.getMyAppliedCompanionList(userId);

        return ResponseEntity.ok(TripmateApiResponse.success(myAppliedCompanionList));
    }

    @Operation(
        summary = "작성한 동행 목록 API",
        description = ""
    )
    @GetMapping("/collect/companions/{userId}")
    public ResponseEntity<TripmateApiResponse<List<MyCollectCompanionListResponse>>> getCollectCompanions(
        @PathVariable("userId") Long userId) {

        List<MyCollectCompanionListResponse> myCollectCompanionList = tripListService.getMyCollectCompanionList(userId);

        return ResponseEntity.ok(TripmateApiResponse.success(myCollectCompanionList));
    }

    @Operation(
        summary = "동행자 선택 API",
        description = ""
    )
    @PostMapping("/choose/companion")
    public ResponseEntity<Void> chooseCompanion(@Valid @RequestBody ChooseCompanionRequest chooseCompanionRequest) {

        tripListService.saveChosenCompanion(chooseCompanionRequest.companionId(), chooseCompanionRequest.userId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
