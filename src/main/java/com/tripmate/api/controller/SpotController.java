package com.tripmate.api.controller;

import com.tripmate.api.domain.spot.Address;
import com.tripmate.api.domain.spot.Location;
import com.tripmate.api.domain.spot.SpotType;
import com.tripmate.api.domain.user.TripmateCharacter;
import com.tripmate.api.domain.user.TripmateCharacterType;
import com.tripmate.api.dto.companion.CompanionRecruitInfo;
import com.tripmate.api.dto.request.LocationBasedSpotSearchRequest;
import com.tripmate.api.dto.response.LocationBasedSpotListResponse;
import com.tripmate.api.dto.response.SpotDetailResponse;
import com.tripmate.api.dto.response.TripmateApiResponse;
import com.tripmate.api.dto.spot.LocationBasedSpotInfo;
import com.tripmate.api.service.CompanionService;
import com.tripmate.api.service.LocationBasedSpotSearchService;
import com.tripmate.integration.tourapi.dto.response.SpotCommonInfo;
import com.tripmate.integration.tourapi.service.TourApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final LocationBasedSpotSearchService locationBasedSpotSearchService;

    private final TourApiService tourApiService;

    private final CompanionService companionService;

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
        SpotCommonInfo spotCommonInfo = tourApiService.getSpotDetailInfo(spotId);
        SpotType spotType = SpotType.fromTourApiCategory(spotCommonInfo.cat2());

        List<CompanionRecruitInfo> companionRecruits = companionService.getCompanionRecruitsBySpot(spotId);

        return ResponseEntity.ok(
                TripmateApiResponse.success(
                        new SpotDetailResponse(
                                spotId,
                                spotCommonInfo.title(),
                                spotCommonInfo.overview(),
                                spotType,
                                spotCommonInfo.firstImage(),
                                spotCommonInfo.tel(),
                                new Location(
                                        spotCommonInfo.mapY(),
                                        spotCommonInfo.mapX(),
                                        new Address(
                                                spotCommonInfo.addr1(),
                                                spotCommonInfo.addr2()
                                        )
                                ),
                                List.of(
                                        new TripmateCharacter("인스타 모험자 아기 돌고래", TripmateCharacterType.DOLPHIN),
                                        new TripmateCharacter("랜드마크 탐험가 아기 펭귄", TripmateCharacterType.PENGUIN),
                                        new TripmateCharacter("인생샷 찍는 인스타 인플루언서 꿀벌", TripmateCharacterType.HONEYBEE)
                                ),
                                companionRecruits
//                                List.of(
//                                        new CompanionRecruitInfo(
//                                                1L,
//                                                new HostInfo(
//                                                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq5wHDD6sXA3M1EhvtDL6MC38-6G27SiCg7g&s",
//                                                        "나는야고윤정",
//                                                        "인스타 인플루언서 아기 펭귄",
//                                                    "PENGUIN",
//                                                        Arrays.asList("맛집탐험형", "액티비티형", "쇼핑형"),
//                                                        70
//                                                ),
//                                                "서피비치 인근에서 같이 식사할 사람 구해요!",
//                                                "여자",
//                                                "20대"
//                                        ),
//                                        new CompanionRecruitInfo(
//                                                2L,
//                                                new HostInfo(
//                                                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq5wHDD6sXA3M1EhvtDL6MC38-6G27SiCg7g&s",
//                                                        "춤추는 심바",
//                                                        "쇼핑을 즐기는 비버",
//                                                    "PENGUIN",
//                                                        Arrays.asList("맛집탐험형", "액티비티형", "쇼핑형"),
//                                                        70
//                                                ),
//                                                "서피비치 인근에서 같이 식사할 사람 구해요!",
//                                                "여자",
//                                                "30대"
//                                        )
//                                )
                        )
                )
        );
    }
}
