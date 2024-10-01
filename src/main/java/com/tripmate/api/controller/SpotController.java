package com.tripmate.api.controller;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.response.LocationBasedSpotListResponse;
import com.tripmate.api.dto.response.SpotDetailResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.api.service.LocationBasedSpotSearchService;
import com.tripmate.api.service.SpotDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotSearchService locationBasedSpotSearchService;

    private final SpotDetailService spotDetailService;

    @Operation(
            summary = "여행지 검색 API",
            description = "위치(위도, 경도) 및 범위, 여행지 타입(예: EXPERIENCE), 여행지 타입 그룹(예: ACTIVITY)로 여행지 탐색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spots"),
    })
    @GetMapping()
    public ResponseEntity<TripmateApiResponse<LocationBasedSpotListResponse>> getSpots(
            @Valid
            @Parameter(description = "여행지 검색 필터(위치 정보 필수)", required = true)
            @ModelAttribute LocationBasedSpotSearchRequest request
    ) {
        List<LocationBasedSpotInfo> spots = locationBasedSpotSearchService.searchLocationBasedSpots(request);
        return ResponseEntity.ok(
                TripmateApiResponse.success(new LocationBasedSpotListResponse(spots))
        );
    }

    @Operation(
            summary = "여행지 상세 API",
            description = "특정 여행지 ID로 여행지 상세정보 탐색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spot detail"),
    })
    @GetMapping("{spotId}")
    public ResponseEntity<TripmateApiResponse<SpotDetailResponse>> getSpotDetail(@PathVariable Long spotId) {
        SpotDetailResponse spotDetailResponse = spotDetailService.getSpotDetail(spotId);

        return ResponseEntity.ok(
                TripmateApiResponse.success(spotDetailResponse)
        );
    }
}
