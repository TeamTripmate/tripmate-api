package com.tripmate.api.controller;

import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.response.LocationBasedSpotListResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.api.service.LocationBasedSpotSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotSearchService locationBasedSpotSearchService;

    @Operation(
            summary = "내 근처 여행지 검색 API",
            description = "위치(위도, 경도), 범위, 카테고리로 여행지 탐색"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spots"),
    })
    @GetMapping()
    public ResponseEntity<TripmateApiResponse<LocationBasedSpotListResponse>> getSpots(
            @Valid
            @Parameter(description = "위치 및 필터 기준(카테고리)", required = true)
            @ModelAttribute LocationBasedSpotSearchRequest request
    ) {
        List<LocationBasedSpotInfo> spots = locationBasedSpotSearchService.searchLocationBasedSpots(request);
        return ResponseEntity.ok(
                TripmateApiResponse.success(new LocationBasedSpotListResponse(spots))
        );
    }
}
