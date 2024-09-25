package com.tripmate.api.controller;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.domain.user.TripmateCharacter;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.dto.companion.CompanionRecruitInfo;
import com.tripmate.api.dto.companion.HostInfo;
import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.response.LocationBasedSpotListResponse;
import com.tripmate.api.dto.response.SpotDetailResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotSearchService locationBasedSpotSearchService;

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
        return ResponseEntity.ok(
                TripmateApiResponse.success(
                        new SpotDetailResponse(
                                spotId,
                                "서핑 체험",
                                "Quae eius ea nihil quasi mollitia consectetur natus repudiandae aut. Sit maiores minus asperiores illum rem. Sunt commodi aliquam ipsam quas aliquam minima.",
                                SpotType.EXPERIENCE,
                                "https://dimg.donga.com/wps/NEWS/IMAGE/2021/07/16/107979650.1.jpg",
                                "010-0000-0000",
                                new Location(
                                        "37.7563022",
                                        "128.922632",
                                        new Address("강원 양양군 현북면 하조대해안길 119", "")
                                ),
                                List.of(
                                        new TripmateCharacter("인스타 모험자 아기 돌고래", TripmateCharacterType.DOLPHIN),
                                        new TripmateCharacter("랜드마크 탐험가 아기 펭귄", TripmateCharacterType.PENGUIN),
                                        new TripmateCharacter("인생샷 찍는 인스타 인플루언서 꿀벌", TripmateCharacterType.HONEYBEE)
                                ),
                                List.of(
                                        new CompanionRecruitInfo(
                                                1L,
                                                new HostInfo(
                                                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq5wHDD6sXA3M1EhvtDL6MC38-6G27SiCg7g&s",
                                                        "나는야고윤정",
                                                        "인스타 인플루언서 아기 펭귄",
                                                        Arrays.asList("맛집탐험형", "액티비티형", "쇼핑형"),
                                                        70
                                                ),
                                                "서피비치 인근에서 같이 식사할 사람 구해요!",
                                                "여자",
                                                "20대"
                                        ),
                                        new CompanionRecruitInfo(
                                                2L,
                                                new HostInfo(
                                                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq5wHDD6sXA3M1EhvtDL6MC38-6G27SiCg7g&s",
                                                        "춤추는 심바",
                                                        "쇼핑을 즐기는 비버",
                                                        Arrays.asList("맛집탐험형", "액티비티형", "쇼핑형"),
                                                        70
                                                ),
                                                "서피비치 인근에서 같이 식사할 사람 구해요!",
                                                "여자",
                                                "30대"
                                        )
                                )
                        )
                )
        );
    }
}
